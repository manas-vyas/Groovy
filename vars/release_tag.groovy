#!/usr/bin/env groovy

def release_tag_check() {
      def commitMessage = sh(returnStdout: true, script: 'git log --format=%B -n 1').trim()
			if (commitMessage.contains('release')) {
			 echo "Commit message contains 'release': $commitMessage"
			 def oldReleaseTag = sh(returnStdout: true, script: 'git describe --abbrev=0 --tags').trim()
			 echo "Old Release Tag: ${oldReleaseTag}"
			 def releaseType = getReleaseType(commitMessage)
			 echo "New Release type: ${releaseType}"
       def newReleaseTag = generateNewReleaseTag(oldReleaseTag, releaseType)
       echo "New Release Tag: $newReleaseTag"
       env.NEW_RELEASE_TAG = newReleaseTag
			 withCredentials([usernamePassword(credentialsId: 'bitbucketPAT', usernameVariable: 'GIT_USERNAME', passwordVariable: 'GIT_PASSWORD')]){
			 //Create the new release tag
       sh "git tag ${newReleaseTag}"
       sh "git remote set-url origin https://${GIT_USERNAME}:${GIT_PASSWORD}@bitbucket.org/rndaxtria/${env.REPO_NAME}"
       sh "git push origin ${newReleaseTag}"
			 }
			} else {
			 echo "Commit message does not contain 'release': $commitMessage"
			}
}

def getReleaseType(commitMessage) {
 if (commitMessage.contains('major')) {
  return 'major'
 } else if (commitMessage.contains('minor')) {
  return 'minor'
 } else if (commitMessage.contains('bugfix')) {
  return 'bugfix'
 } else {
  return 'unknown'
 }
}



def generateNewReleaseTag(oldReleaseTag, releaseType) {
 def version = oldReleaseTag =~ /(\d+\.\d+\.\d+)/ // Extract the version using regex
 def currentVersion = version[0][0]
 def newVersion

 if (releaseType == 'major') {
  newVersion = incrementVersionComponent(currentVersion, 0)
  def newVersionArray = newVersion.tokenize('.')
  newVersionArray[1] = '0'
  newVersionArray[2] = '0'
  newVersion = newVersionArray.join('.')
 } else if (releaseType == 'minor') {
  newVersion = incrementVersionComponent(currentVersion, 1)
  def newVersionArray = newVersion.tokenize('.')
  newVersionArray[2] = '0'
  newVersion = newVersionArray.join('.')
 } else if (releaseType == 'bugfix') {
  newVersion = incrementVersionComponent(currentVersion, 2)
 } else {
  // Unknown release type, return the old tag as is
  return oldReleaseTag
 }
 return oldReleaseTag.replaceFirst(currentVersion, newVersion)
}

def incrementVersionComponent(version, componentIndex) {
 def versionArray = version.tokenize('.')
 def componentValue = versionArray[componentIndex].toInteger()
 componentValue++
 versionArray[componentIndex] = componentValue.toString()
 return versionArray.join('.')
}
