def call(service, tag, datetime, url, region) { 
  echo service
  echo tag
  def ScriptAsaString = libraryResource 'update_confluence.py' 
  writeFile file: 'update_confluence.py', text: ScriptAsaString
  sh "python /var/lib/jenkins/confluence/update_confluence.py '$service' '$tag' '$datetime' '$url' '$region'"
}
