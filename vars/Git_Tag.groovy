#!/usr/bin/env groovy

def tag_name() {
      if (require_tag == "Yes"){	  	  
      //sh "git checkout ${REPO_BRANCH}"
      //check the latest commit id from the branch
		  commit_id = sh (returnStdout: true, script: 'git rev-parse HEAD').trim()
		  //export date_time = sh (returnStdout: true, script: 'git show -s --format=%ci ${commit_id}').trim()
		  sh "git tag ${Name_tag} ${commit_id}"
		  sh "git remote set-url origin https://${USER}:${PASSWD}@${Tag_path}"
		  sh "git push origin ${Name_tag}"

	   }

   }
