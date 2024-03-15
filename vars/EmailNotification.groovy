def call() {
  // emailext subject: "${currentBuild.currentResult}: Job at STAGE ${env.STAGE_NAME} ${env.JOB_NAME} - ${env.BUILD_NUMBER}", attachLog: true, attachmentsPattern: '*.log', body: """<br><p><p>Check console output at &QUOT;<a href='${env.BUILD_URL}console'>${env.JOB_NAME} - ${env.BUILD_NUMBER}</a>&QUOT;</p><br>""", mimeType: 'text/html', recipientProviders: [requestor()], to: ''

  emailext (body: '''${SCRIPT, template="groovy-html-1.template"}''', mimeType: 'text/html', subject: "${currentBuild.currentResult}: Build ${env.JOB_NAME} - ${env.BUILD_NUMBER}", attachLog: false, recipientProviders: [requestor()], to: "manashwiv@epiqinfo.com")

}
