./mvnw clean package
dos2unix docker-entrypoint.sh || true
docker build -t dockerlab.asc.de:8443/checklist_backend:1.0 .
./mvnw clean

cd frontend
sh build.sh
cd ..
