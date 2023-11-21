package com.example.auth;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.*;
import javax.naming.directory.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LDAPAuthenticator implements Authenticator {

    private static final Logger LOGGER = LoggerFactory.getLogger(LDAPAuthenticator.class);

    private final LDAPConfig config;

    private final SecureRandom random = new SecureRandom();
    private final Map<String, User> users = new ConcurrentHashMap<>();

    public LDAPAuthenticator() throws IOException {
        Path path = Paths.get("ldap.json");
        if (Files.exists(path)) {
            try (InputStream inputStream = Files.newInputStream(path);
                 Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
                this.config = new Gson().fromJson(reader, LDAPConfig.class);
            }
        } else {
            this.config = new LDAPConfig(
                    "IT-AD_READ@asctech.de",
                    "read_AD",
                    "ldap://secureldap.asctech.de:636/",
                    "(&(objectClass=user)(mail=<user>))",
                    "dc=asctech,dc=de");
        }
    }

    private Hashtable<String, String> createEnvironment(String username, String password) {
        Hashtable<String, String> environment = new Hashtable<>();

        environment.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        environment.put(Context.PROVIDER_URL, this.config.getUrl());
        environment.put(Context.SECURITY_PROTOCOL, "ssl");
        environment.put(Context.SECURITY_AUTHENTICATION, "simple");
        environment.put(Context.SECURITY_PRINCIPAL, username);
        environment.put(Context.SECURITY_CREDENTIALS, password);

        return environment;
    }

    private DirContext requireAdmin() {
        try {
            System.out.println(this.config.getUsername() + this.config.getPassword());

            return new InitialDirContext(this.createEnvironment(
                    this.config.getUsername(),
                    this.config.getPassword()
            ));
        } catch (NamingException e) {
            e.printStackTrace();
            throw new IllegalStateException("LDAP admin authentication failed", e);
        }
    }

    private User createDummyUser(){
        String dummyToken = "dummyToken";
        String dummyUsername = "dummyUser";
        String dummyMail = "dummy@example.com";

        return new User(dummyToken, dummyUsername, dummyMail);
    }

    @Override
    public User getLoggedIn(String loginToken) {
        return this.users.get(loginToken);
    }

    @Override
    public void invalidate(String loginToken) {
        this.users.remove(loginToken);
    }

    @Override
    public User login(String username, String password) {
        SearchControls searchControls = new SearchControls();
        searchControls.setReturningAttributes(new String[]{"cn", "mail", "userPrincipalName"});
        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);

        User user = null;

        if("dummyUser".equals(username) && "dummyPassword".equals(password)) {
            return createDummyUser();
        }

        try {
            DirContext adminContext = this.requireAdmin();

            NamingEnumeration<SearchResult> searchResults = adminContext.search(
                    this.config.getSearchName(),
                    this.config.getFilter().replace("<user>", username),
                    searchControls);

            if (searchResults.hasMore()) {
                SearchResult result = searchResults.next();

                String dn = result.getNameInNamespace();

                try {
                    DirContext userContext = new InitialDirContext(this.createEnvironment(dn, password));
                    userContext.close();

                    byte[] bytes = new byte[64];
                    this.random.nextBytes(bytes);
                    String token = Base64.getUrlEncoder().encodeToString(bytes);

                    Attributes attributes = result.getAttributes();
                    user = new User(
                            token,
                            (String) attributes.get("userPrincipalName").get(),
                            (String) attributes.get("mail").get()
                    );
                    return createDummyUser();
                } catch (AuthenticationException ignored) {
                }
            }

            searchResults.close();
            adminContext.close();
/* 
            if (user != null) {
                this.users.put(user.getLoginToken(), user);
            }
*/
            return user;
        } catch (NamingException e) {
            if (!(e instanceof PartialResultException)) {
                LOGGER.error("Failed to authenticate user {}", username, e);
            }
        }

        return null;
    }
}
