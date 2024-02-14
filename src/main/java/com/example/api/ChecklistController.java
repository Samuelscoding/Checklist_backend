package com.example.api;

import com.example.db.ChecklistDAO;
import com.example.db.ChecklistItem;
import com.example.db.Version;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.javalin.http.Handler;

import java.time.LocalDate;
import java.util.List;

public class ChecklistController {

    private final ChecklistDAO checklistDAO = new ChecklistDAO();

    // Handler für das Senden von Reminder-E-Mails
    public Handler sendReminderEmail = ctx -> {
        try {
            String jsonBody = ctx.body();
            JsonObject jsonObject = JsonParser.parseString(jsonBody).getAsJsonObject();
            String to = jsonObject.get("to").getAsString();
            String subject = jsonObject.get("subject").getAsString();
            String body = jsonObject.get("body").getAsString();

            // Überprüfen der Werte 
            System.out.println("To: " + to);
            System.out.println("Subject: " + subject);
            System.out.println("Body: " + body);

            EmailController.sendEmail(to, subject, body);

            ctx.status(200).result("Reminder email sent successfully");
        } catch(Exception e) {
            e.printStackTrace();
            ctx.status(500).result("Error sending reminder email");
        }
    };

    // Überprüfung, welche Art von Aufgabe
    private LocalDate calculatePlannedDate(ChecklistItem checklistItem, String version) {
        LocalDate plannedDate;
        if("Preliminary-row".equals(checklistItem.getColorClass_pv())) {
            plannedDate = calculatePreliminaryReleaseDate(checklistItem, version);
        } else if("Release-row".equals(checklistItem.getColorClass_rv())) {
            plannedDate = calculateFinalReleaseDate(checklistItem, version);
        } else {
            	plannedDate = calculateCustomDate(checklistItem, version);
        }
        return plannedDate;
    }

    // Handler um Datum von Preliminary-Aufgaben zu berechnen
    private LocalDate calculatePreliminaryReleaseDate(ChecklistItem checklistItem, String version) {
        LocalDate referenceDate;

        if("Die Manuals des Manual Package (deutsch und englisch, freigegebene Version) wurden aktualisiert - Preliminary Version".equals(checklistItem.getTask()) ||
           "Die Service Manuals (deutsch und englisch, freigegebene Version) wurden aktualisiert - Preliminary Version".equals(checklistItem.getTask()) ||
           "Das Dokument \"Release Announcement\" liegt vor - Preliminary Version".equals(checklistItem.getTask()) ||
           "Das Dokument \"Version Matrix\" liegt vor - Preliminary Version".equals(checklistItem.getTask()) ||
           "Das Dokument \"Release Announcement\" wurde verteilt  - Preliminary Version".equals(checklistItem.getTask())) {
            referenceDate = checklistDAO.getPreliminaryReleaseDate(version);
            int daysDifference = 0;
            return referenceDate.minusDays(daysDifference);

        } else if("Die Freigabeempfehlung liegt vor - Preliminary Version".equals(checklistItem.getTask())) {
            referenceDate = checklistDAO.getPreliminaryReleaseDate(version);
            int daysDifference = 4;
            return referenceDate.minusDays(daysDifference);

        } else if("Die Feature-Liste wurde aktualisiert - Preliminary Version".equals(checklistItem.getTask())) {
            referenceDate = checklistDAO.getPreliminaryReleaseDate(version);
            int daysDifference = 2;
            return referenceDate.minusDays(daysDifference);

        } else {
            return null;
        }
    }

    // Handler um Datum von Release-Aufgaben zu berechnen
    private LocalDate calculateFinalReleaseDate(ChecklistItem checklistItem, String version) {
        LocalDate referenceDate;

        if("Die Manuals des Manual Package (deutsch und englisch, freigegebene Version) wurden aktualisiert - Release Version".equals(checklistItem.getTask()) ||
           "Das Dokument \"Release Announcement\" liegt vor - Release Version".equals(checklistItem.getTask()) ||
           "Das Dokument \"Version Matrix\" liegt vor - Release Version".equals(checklistItem.getTask()) ||
           "Das Dokument \"Release Announcement\" wurde verteilt  - Release Version".equals(checklistItem.getTask())) {
            referenceDate = checklistDAO.getFinalReleaseDate(version);
            int daysDifference = 0;
            return referenceDate.minusDays(daysDifference);

        } else if("Die Feature-Liste wurde aktualisiert  - Release Version".equals(checklistItem.getTask())) {
            referenceDate = checklistDAO.getFinalReleaseDate(version);
            int daysDifference = 7;
            return referenceDate.minusDays(daysDifference);

        } else if("Die Freigabeempfehlung liegt vor - Release Version".equals(checklistItem.getTask())) {
            referenceDate = checklistDAO.getFinalReleaseDate(version);
            int daysDifference = 5;
            return referenceDate.minusDays(daysDifference);
        } else {
            return null;
        } 
    }

    // Handler um Datum von normalen Aufgaben zu berechnen
    private LocalDate calculateCustomDate(ChecklistItem checklistItem, String version) {
        LocalDate referenceDate;

        if("Die ASC-Product-Roadmap ist aktualisiert".equals(checklistItem.getTask()) || 
           "Die Detaillierung der Features für den Sprint wurden im Issue & Project Tracking Tool durchgeführt".equals(checklistItem.getTask()) ||
           "Die Releaseplanung wurde im Issue & Project tracking Tool abgeschlossen".equals(checklistItem.getTask())) {
            referenceDate = checklistDAO.getPreliminaryReleaseDate(version);
            int daysDifference = 34;
            return referenceDate.minusDays(daysDifference);

        } else  if("Die Sammlung der Features im Backlog des Issue & Project Tracking Tool ist erfolgt".equals(checklistItem.getTask())) {
            referenceDate = checklistDAO.getPreliminaryReleaseDate(version);
            int daysDifference = 55;
            return referenceDate.minusDays(daysDifference);

        } else if("Die aktuelle Priorisierung des Backlog im Issue & Project Tracking Tool wurde durchgeführt".equals(checklistItem.getTask())) {
            referenceDate = checklistDAO.getPreliminaryReleaseDate(version);
            int daysDifference = 41;
            return referenceDate.minusDays(daysDifference);

        } else if("Die Tasks zu den Features für den Sprint wurden angelegt".equals(checklistItem.getTask()) ||
                  "Der Projektplan der Entwicklung liegt vor".equals(checklistItem.getTask()) ||
                  "Das Testkonzept (R&D, QA und P&P) liegt vor und wurde mit QA abgestimmt".equals(checklistItem.getTask()) ||
                  "Die Materialkalkulation liegt vor".equals(checklistItem.getTask())) { 
            referenceDate = checklistDAO.getPreliminaryReleaseDate(version);
            int daysDifference = 20;
            return referenceDate.minusDays(daysDifference);

        } else if("Die Prüfpläne (System Test & Release) liegen vor".equals(checklistItem.getTask())) {
            referenceDate = checklistDAO.getPreliminaryReleaseDate(version);
            int daysDifference = 0;
            return referenceDate.minusDays(daysDifference);

        } else if("Sprint 1 wurde durchgeführt".equals(checklistItem.getTask())) {
            referenceDate = checklistDAO.getPreliminaryReleaseDate(version);
            int daysDifference = 2;
            return referenceDate.minusDays(daysDifference);

        } else if("Die Entwicklung der Version ist abgeschlossen und die Integrationstests haben begonnen".equals(checklistItem.getTask())) {
            referenceDate = checklistDAO.getPreliminaryReleaseDate(version);
            int daysDifference = 19;
            return referenceDate.plusDays(daysDifference);

        } else if("Die Vorlage der Test Reports für die Produktion wurde aktualisiert".equals(checklistItem.getTask()) ||
                  "Die Kompatibilitätsliste wurde aktualisiert".equals(checklistItem.getTask()) ||
                  "Das Dokument \"Neo Integration Overview\" wurde aktualisiert".equals(checklistItem.getTask()) ||
                  "Das Dokument \"Neo Sizing Guide\" wurde aktualisiert".equals(checklistItem.getTask()) ||
                  "Die Power Point Präsentationen (deutsch und englisch) wurden aktualisiert".equals(checklistItem.getTask()) ||
                  "Die Schulungen für Order Processing und Sales (vertrieblich) sind durchgeführt".equals(checklistItem.getTask()) ||
                  "Die Schulungen für definierte ASC - Partner (vertrieblich) sind durchgeführt".equals(checklistItem.getTask()) ||
                  "Das Installations- Updatemedium ist erstellt".equals(checklistItem.getTask()) ||
                  "Die Betreuung durch Technical Support Center und Service & Maintenance (HQ und international) ist sichergestellt".equals(checklistItem.getTask()) ||
                  "Die Ersatzteilbevorratung bei ASC ist sichergestellt".equals(checklistItem.getTask())) {
            referenceDate = checklistDAO.getFinalReleaseDate(version);
            int daysDifference = 7;
            return referenceDate.minusDays(daysDifference);

        } else if("Das Manual Package (englisch, deutsch) wurde erstellt".equals(checklistItem.getTask())) {
            referenceDate = checklistDAO.getFinalReleaseDate(version);
            int daysDifference = 0;
            return referenceDate.minusDays(daysDifference);

        } else if("Der finale Sprint wurde durchgeführt".equals(checklistItem.getTask())) {
            referenceDate = checklistDAO.getFinalReleaseDate(version);
            int daysDifference = 24;
            return referenceDate.minusDays(daysDifference);

        } else if("Das Freigabedokument zur Versionsfreigabe ist erstellt und wird den Teammitgliedern zur Unterschrift vorgelegt".equals(checklistItem.getTask()) ||
                  "Die Systemtests wurden bis zum Abdeckungsziel abgeschlossen und liegen protokolliert vor (entspricht Synchronisationspunkt S 3)".equals(checklistItem.getTask()) ||
                  "Das Issue & Project tracking Tool wurde bezüglich der getesteten Version aktualisiert".equals(checklistItem.getTask()) ||
                  "Die Qualitätsüberwachung ist vorhanden".equals(checklistItem.getTask())) {
            referenceDate = checklistDAO.getFinalReleaseDate(version);
            int daysDifference = 3;
            return referenceDate.minusDays(daysDifference);

        } else if("Die Vorgehensweise für die Materialbestellung (Hard- und Software) ist geregelt".equals(checklistItem.getTask()) ||
                  "Die Integrationstests der Version sind abgeschlossen und die Software / Hardware wurde an System Test & Release übergeben".equals(checklistItem.getTask())) {
            referenceDate = checklistDAO.getFinalReleaseDate(version);
            int daysDifference = 42;
            return referenceDate.minusDays(daysDifference);

        } else if("Die Schulungen für Production & Process F&Ctolling (technisch) sind durchgeführt".equals(checklistItem.getTask()) ||
                  "Die Schulungen für Technical Support Center (technisch) sind durchgeführt".equals(checklistItem.getTask()) ||
                  "Die Schulungen für Service & Maintenance (technisch) sind durchgeführt".equals(checklistItem.getTask()) ||
                  "Die Angebots- Verkaufstexte und Verkaufspreise (ERP-System) wurden aktualisiert".equals(checklistItem.getTask()) ||
                  "Die Auftragsabwicklung International ist sichergestellt".equals(checklistItem.getTask()) ||
                  "Die Auftragsabwicklung Germany ist sichergestellt".equals(checklistItem.getTask())) {
            referenceDate = checklistDAO.getFinalReleaseDate(version);
            int daysDifference = 14;
            return referenceDate.minusDays(daysDifference);

        } else if("Die Angebots- Verkaufsartikel (ERP-System) wurden aktualisiert".equals(checklistItem.getTask())) {
            referenceDate = checklistDAO.getFinalReleaseDate(version);
            int daysDifference = 21;
            return referenceDate.minusDays(daysDifference);

        } else if("Das Tool \"Test Protocol\" wurde bezüglich der neuen Version des Produkts aktualisiert".equals(checklistItem.getTask())) {
            referenceDate = checklistDAO.getFinalReleaseDate(version);
            int daysDifference = 35;
            return referenceDate.minusDays(daysDifference);

        } else {
            return null;
        }
    }

    // Handler um Aufgaben zu importieren
    public Handler importChecklistItems = ctx -> {
        try {
            ImportChecklistRequest importRequest = ctx.bodyAsClass(ImportChecklistRequest.class);
            String version = importRequest.getVersion();
            List<ChecklistItem> importedItems = importRequest.getChecklistItems();

            for(ChecklistItem item : importedItems) {
                item.setPlannedDate(calculatePlannedDate(item, version));
            }

            checklistDAO.replaceChecklistItems(version, importedItems);
            ctx.status(200).json(importedItems);
        } catch(Exception e) {
            e.printStackTrace();
            ctx.status(500).json("Error importing checklist items");
        }

    };

    public static class ImportChecklistRequest {
        private String version;
        private List<ChecklistItem> checklistItems;

        public String getVersion() {
            return version;
        }

        public List<ChecklistItem> getChecklistItems() {
            return checklistItems;
        }
    }

    // Handler um Version abzurufen
    public Handler getVersions = ctx -> {
        List<Version> versions = checklistDAO.getVersions();
        ctx.json(versions);
    };

    // Handler um neue Version hinzuzufügen
    public Handler addVersion = ctx -> {
        Version newVersion = ctx.bodyAsClass(Version.class);
        checklistDAO.addVersion(newVersion);
        ctx.status(201).json(newVersion);
    };

    // Handler um Version zu bearbeiten
    public Handler editVersion = ctx -> {
        Version editedVersion = ctx.bodyAsClass(Version.class);
        checklistDAO.editVersion(editedVersion);
        ctx.status(200).json(editedVersion);
    };

    // Handler um Version zu löschen
    public Handler deleteVersion = ctx -> {
        String versionName = ctx.pathParam("versionName");
        checklistDAO.deleteVersion(versionName);
        ctx.status(204);
    };

    // Handler um Version freizugeben
    public Handler completeVersion = ctx -> {
        int versionId = Integer.parseInt(ctx.pathParam("id"));
        Version updatedVersion = ctx.bodyAsClass(Version.class);
    
        // Überprüfen, ob die Version gültig ist (z.B. ob sie in der Datenbank existiert)
        Version existingVersion = checklistDAO.getVersionById(versionId);
        if (existingVersion == null) {
            ctx.status(404).result("Version not found");
            return;
        }
    
        // Aktualisieren der spezifischen Felder
        if (updatedVersion.getFinishedDate() != null) {
            existingVersion.setFinishedDate(updatedVersion.getFinishedDate());
        }
        if (updatedVersion.getSignature() != null) {
            existingVersion.setSignature(updatedVersion.getSignature());
        }
        existingVersion.setReleased(updatedVersion.isReleased());
    
        // Aktualisieren der Version in der Datenbank
        checklistDAO.completeVersion(existingVersion);
    
        ctx.json(existingVersion); // Rückgabe der aktualisierten Version
    };

    // Handler um alle Aufgaben für den Admin zu erhalten
    public Handler getChecklistItemsForAdmin = ctx -> {

        List<ChecklistItem> checklistItems;

        String departmentFilter = ctx.queryParam("department");
        String versionFilter = ctx.queryParam("version");
        boolean showIncompleteTasks = ctx.queryParam("showIncompleteTasks") != null;


        if(versionFilter != null && !versionFilter.isEmpty()) {
            if(showIncompleteTasks) {
                if (departmentFilter != null && !departmentFilter.isEmpty()) {
                    checklistItems = checklistDAO.getIncompleteChecklistItemsByDepartmentAndVersion(departmentFilter, versionFilter);
                } else {
                    checklistItems = checklistDAO.getIncompleteChecklistItemsByVersion(versionFilter);
                }
            } else {
                if (departmentFilter != null && !departmentFilter.isEmpty()) {
                    checklistItems = checklistDAO.getChecklistItemsByDepartmentAndVersion(departmentFilter, versionFilter);
                } else {
                    checklistItems = checklistDAO.getChecklistItemsByVersion(versionFilter);
                }
            }
        } else if(departmentFilter != null && !departmentFilter.isEmpty()) {
            if(showIncompleteTasks) {
                checklistItems = checklistDAO.getIncompleteChecklistItemsByDepartment(departmentFilter);
            } else {
                checklistItems = checklistDAO.getChecklistItemsByDepartment(departmentFilter);
            }
        } else {
            if(showIncompleteTasks) {
                checklistItems = checklistDAO.getIncompleteChecklistItems();
            } else {
                checklistItems = checklistDAO.getChecklistItems();  
            }
        }

        for(ChecklistItem item : checklistItems) {
            item.setPlannedDate(calculatePlannedDate(item, item.getVersion()));
        }

        ctx.json(checklistItems);
    };

    // Handler um Aufgaben des Nutzers zu laden
    public Handler getChecklistItemsforUser = ctx -> {
        List<ChecklistItem> checklistItems;

        String departmentFilter = ctx.queryParam("department");
        String versionFilter = ctx.queryParam("version");
        boolean showIncompleteTasks = ctx.queryParam("showIncompleteTasks") != null;
        String username = ctx.queryParam("username");

        if (versionFilter != null && !versionFilter.isEmpty()) {
            if (showIncompleteTasks) {
                if (departmentFilter != null && !departmentFilter.isEmpty()) {
                    checklistItems = checklistDAO.getIncompleteChecklistItemsByDepartmentAndVersionForUser(departmentFilter, versionFilter, username);
                } else {
                    checklistItems = checklistDAO.getIncompleteChecklistItemsByVersionForUser(versionFilter, username);
                }
            } else {
                if (departmentFilter != null && !departmentFilter.isEmpty()) {
                    checklistItems = checklistDAO.getChecklistItemsByDepartmentAndVersionForUser(departmentFilter, versionFilter, username);
                } else {
                    checklistItems = checklistDAO.getChecklistItemsByVersionForUser(versionFilter, username);
                }
            }
        } else if (departmentFilter != null && !departmentFilter.isEmpty()) {
            if (showIncompleteTasks) {
                checklistItems = checklistDAO.getIncompleteChecklistItemsByDepartmentForUser(departmentFilter, username);
            } else {
                checklistItems = checklistDAO.getChecklistItemsByDepartmentForUser(departmentFilter, username);
            }
        } else {
            if (showIncompleteTasks) {
                checklistItems = checklistDAO.getIncompleteChecklistItemsForUser(username);
            } else {
                checklistItems = checklistDAO.getChecklistItemsForUser(username);
            }
        }

        for(ChecklistItem item : checklistItems) {
            item.setPlannedDate(calculateCustomDate(item, item.getVersion()));
        }
        
        ctx.json(checklistItems);
    };

    public Handler addItemToChecklist = ctx -> {
        ChecklistItem newItem = ctx.bodyAsClass(ChecklistItem.class);
        checklistDAO.addItemToChecklist(newItem);
        ctx.status(201).json(newItem);
    };

    public Handler deleteItemFromChecklist = ctx -> {
        int taskId = ctx.pathParamAsClass("taskId", Integer.class).get();
        checklistDAO.deleteItemFromChecklist(taskId);
        ctx.status(204);
    };

    public Handler updateItemInChecklist = ctx -> {
        ChecklistItem updatedItem = ctx.bodyAsClass(ChecklistItem.class);
        LocalDate plannedDate = calculatePlannedDate(updatedItem, updatedItem.getVersion());
        updatedItem.setPlannedDate(plannedDate);
        checklistDAO.updateItemInChecklist(updatedItem);

        ctx.status(200).json(updatedItem);
    };
}