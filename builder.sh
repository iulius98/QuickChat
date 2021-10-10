serverFolder=server
uiFolder=ui
staticFolder=$serverFolder/src/main/resources/static
jarFile=quickchat-0.0.1-SNAPSHOT.jar
echo ===============start ui build================================
cd ui
npm install
npm run build
cd ..
echo ==========ui is build=========================================
if [ -d "$staticFolder" ]; then
	echo ==========delete previous resources/static ===================
	rm -r $serverFolder/src/main/resources/static
fi

echo ==========create new resouces/static===========================
mkdir $serverFolder/src/main/resources/static

echo ==========copy ui build into new static========================
cp -a $uiFolder/build/. $staticFolder

echo ==========build principal project===============================
cd $serverFolder
sudo chmod +x mvnw
./mvnw package
cd ..


if [ -f "$jarFile" ]; then
	echo ==========remove current jar project============================
	rm $jarFile
fi

echo ==========move new jar folder in the root directory==============
mv $serverFolder/target/$jarFile ./
