
# update package list
# - http://askubuntu.com/questions/222348/what-does-sudo-apt-get-update-do
sudo apt-get update
	
# install the add-apt-repository command
# - http://stackoverflow.com/questions/13018626/add-apt-repository-not-found
sudo apt-get -y install software-properties-common python-software-properties
which add-apt-repository # verify

# install Git
# - use ppa to get a recent version, older versions may not work with Jenkins
# - http://askubuntu.com/questions/279172/upgrade-git-on-ubuntu-10-04-lucid-lynx
sudo add-apt-repository -y ppa:git-core/ppa
sudo apt-get update
sudo apt-get -y install git
git --version # verify
# git config --global user.name "<your name>"
# git config --global user.email "<your email>"

# install Java 7
# - neccessary to run scripts even if you have Jenkins auto install Java
# - http://openjdk.java.net/install/
sudo apt-get install -y openjdk-7-jdk
java -version # verify

# install Maven
# - not necessary if you have Jenkins auto install Maven
# - http://stackoverflow.com/questions/15630055/how-to-install-maven-3-on-ubuntu-12-04-12-10-13-04-13-10-by-using-apt-get
sudo apt-get -y install maven
mvn -version # verify

# install xfsprogs (for mounting EBS)
# - http://www.linuxfromscratch.org/blfs/view/svn/postlfs/xfsprogs.html
sudo apt-get -y install xfsprogs
which mkfs.xfs # verify

# install Firefox for browser testing
# - http://askubuntu.com/questions/6339/how-do-i-install-the-latest-stable-version-of-firefox
# - this is for the continuous deployment tutorial with selenium
sudo apt-get -y install firefox

# install Xvfb for headless browser testing
# - this is for the continuous deployment tutorial with selenium
sudo apt-get -y install xvfb

# install common text editors
# - these are optional
sudo apt-get -y install emacs vim

# install Jenkins
# - https://wiki.jenkins-ci.org/display/JENKINS/Installing+Jenkins+on+Ubuntu
wget -q -O - http://pkg.jenkins-ci.org/debian/jenkins-ci.org.key | sudo apt-key add -
sudo sh -c 'echo deb http://pkg.jenkins-ci.org/debian binary/ > /etc/apt/sources.list.d/jenkins.list'
sudo apt-get update
sudo apt-get -y install jenkins

# verify Jenkins installation
ls /etc/init.d/jenkins
ls /etc/default/jenkins
ls /var/log/jenkins/jenkins.log

