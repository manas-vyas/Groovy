def tagpushDev() {
  sh 'docker tag $DOCKER_BUILD_TAG:latest ${ECR}:${PRODUCT}_"${repo}"_${BUILD_ID}'
  sh 'docker tag ${DOCKER_BUILD_TAG}:latest ${ECR}:${PRODUCT}_latest'
  sh 'docker push ${ECR}:${PRODUCT}_${repo}_${BUILD_ID}'
  sh 'docker push ${ECR}:${PRODUCT}_latest'
  }

def tagpushQa() {
  sh 'docker tag $DOCKER_BUILD_TAG:latest ${ECR}:${PRODUCT}_"${repo}"_${BUILD_ID}'
  sh 'docker tag ${DOCKER_BUILD_TAG}:latest ${ECR}:${PRODUCT}_qa_latest'
  sh 'docker push ${ECR}:${PRODUCT}_${repo}_${BUILD_ID}'
  sh 'docker push ${ECR}:${PRODUCT}_qa_latest'
  }
