#!/usr/bin/env groovy

def deploy() {
  sh "aws elasticbeanstalk create-application-version --application-name ${AWS_EB_APP_NAME} --version-label ${BUILD_TAG} --source-bundle S3Bucket=$AWS_S3_BUCKET,S3Key=${BUILD_TAG}.zip"
  sh "aws elasticbeanstalk update-environment --application-name ${AWS_EB_APP_NAME} --environment-name ${AWS_EB_ENVIRONMENT} --version-label ${BUILD_TAG}"
  sleep 10L // wait for beanstalk to update the HealthStatus
  timeout(time: 10, unit: 'MINUTES') {
  waitUntil {
  sh "aws elasticbeanstalk describe-environment-health --environment-name ${AWS_EB_ENVIRONMENT} --attribute-names All > .beanstalk-status.json"
  // parse `describe-environment-health` output
  def beanstalkStatusAsJson = readFile(".beanstalk-status.json")
  def beanstalkStatus = new groovy.json.JsonSlurper().parseText(beanstalkStatusAsJson)
  println "$beanstalkStatus"
  return beanstalkStatus.HealthStatus == "Ok" && beanstalkStatus.Status == "Ready"
    } 
  } 
 }

