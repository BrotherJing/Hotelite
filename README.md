first install sqlite:
apt-get install sqlite sqlite3
apt-get install libsqlite3-dev

apt-get install maven2

mvn archetype:generate
-DgroupId=com.companyname.bank
-DartifactId=consumerBanking
-DarchetypeArtifactId=maven-archetype-quickstart
-DinteractiveMode=false
-DarchetypeCatalog=internal
