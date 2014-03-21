
# ==============================================================================
# configuration
# ==============================================================================

# directories
JENKINS_USER_HOME=/var/lib/jenkins/
TUTORIAL_DIR=$JENKINS_USER_HOME/jenkins-tutorial
CODE_DIR=$TUTORIAL_DIR/jenkins-in-ec2
CONF_DIR=$TUTORIAL_DIR/conf

# script paths
JAR=$CODE_DIR/target/jenkins-in-ec2-0.0.1-jar-with-dependencies.jar
SCRIPT=com.stevewedig.blog.jenkins.scripts.JenkinsWakeup 

# properties paths
AWS_CREDENTIALS=$CONF_DIR/AwsCredentials.properties
JENKINS_CONF=$CONF_DIR/JenkinsConf.properties

# unix device
EBS_VOLUME_UNIX_DEVICE=/dev/xvdj # must match the setting in $JENKINS_CONF

#  mount location
EBS_JENKINS_HOME=/jenkins_ebs # must match JENKINS_HOME in /etc/default/jenkins

# ==============================================================================
# already mounted?
# ==============================================================================

# this runs anytime you do a "service jenkins xxx" command, so only run it the first time 
if [ -e '$EBS_JENKINS_HOME' ]; then echo "EBS volume already mounted: $EBS_JENKINS_HOME"; exit 0; fi

# ==============================================================================
# get EC2 instance id
# ==============================================================================

# http://stackoverflow.com/questions/625644/find-out-the-instance-id-from-within-an-ec2-machine/628037#628037
echo "getting EC2 instance id"
die() { status=$1; shift; echo "FATAL: $*"; exit $status; }
EC2_INSTANCE_ID="`wget -q -O - http://169.254.169.254/latest/meta-data/instance-id || die \"wget instance-id has failed: $?\"`"
echo "got EC2 instance id: $EC2_INSTANCE_ID"

# ==============================================================================
# attach and mount EBS volume
# ==============================================================================

# attach
echo "running attach script"
java -cp $JAR $SCRIPT $AWS_CREDENTIALS $JENKINS_CONF $EC2_INSTANCE_ID 

# mount
echo "make $EBS_JENKINS_HOME"
mkdir -p -m 000 $EBS_JENKINS_HOME

echo "mounting device"
mount $EBS_VOLUME_UNIX_DEVICE $EBS_JENKINS_HOME

echo "change owner to jenkins"
chown jenkins $EBS_JENKINS_HOME

# had a timing issue, don't care to find out more, this fixes it 
sleep 15

