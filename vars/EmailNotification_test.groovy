def call() {

  emailext subject: "${currentBuild.currentResult}: Job at STAGE ${env.STAGE_NAME} ${env.JOB_NAME} - ${env.BUILD_NUMBER}", attachLog: true, attachmentsPattern: '*.log', body: '''${SCRIPT, template="jenkins-matrix-email-html.template"}''', mimeType: 'text/html', recipientProviders: [requestor()], to: 'manashwiv@epiqinfo.com'

}
