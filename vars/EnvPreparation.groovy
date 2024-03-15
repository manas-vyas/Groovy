def copyartifact() {
	  sh 'aws s3 cp s3://s3-insightsmax-orch/EB_config/$ARTIFACT_NAME .'
		sh 'mkdir -p temp'
		sh 'unzip -u ./$ARTIFACT_NAME -d temp'
}

def copyartifactDV() {
	  sh 'aws s3 cp s3://s3-insightsmax-orch/EB_config/$ARTIFACT_NAME .'
		sh 'mkdir -p temp'
		sh 'unzip -u ./$ARTIFACT_NAME -d temp'
		sh 'mkdir -p ./temp/insightsmax-data-visualization'
		sh 'touch ./temp/insightsmax-data-visualization/export_env_variables.sh'
		sh'''
		temp_data=$(aws secretsmanager get-secret-value --secret-id $AWS_SECRET_ID --query 'SecretString' --output text)
		python secretManagerToEnvParser.py "$temp_data" "./temp/insightsmax-data-visualization/export_env_variables.sh"
		cat './temp/insightsmax-data-visualization/export_env_variables.sh'
		'''
		sh 'chmod -R 777 ./temp/insightsmax-data-visualization/export_env_variables.sh'
}
def removeFile() {				
	//sh 'cat ./temp/Dockerrun.aws.json'
	sh 'rm -rf $ARTIFACT_NAME'
	sh 'mkdir manas'
	sh 'ls -lart temp'
	sh 'cd temp && zip -r ../$ARTIFACT_NAME .'
	sh "aws s3 cp ./$ARTIFACT_NAME s3://$AWS_S3_BUCKET/${BUILD_TAG}.zip"
}
