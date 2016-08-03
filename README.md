# Prerequisites  #
## Configure AWS credentials ##
You need to have you AWS access key id and your AWS secret access key.
These keys shall be stored in a filed named *credentials* located in the ~/.aws folder.
The content of the file shall be as follows:

````
[default] 
aws_access_key_id = <your_access_key_id>  
aws_secret_access_key = <your_secret_access_key>
````

## Install and configure Docker ##
1. Install [Docker](https://docs.docker.com)
2. Login to dockerhub. 
````
$ docker login
````

## Install AWS CLI ##
[AWS Command Line Interface](https://aws.amazon.com/cli/)

# Create the AWS infrastructure stack #
In order to run our microservice application demo we need an infrastructure stack to run on. We will use AWS Cloudformation to create the stack. The Cloudformation templates to create the stack are available in this repository but they have also been stored in an AWS S3 bucket.

1. First login to the AWS management console using your username and password. In order to do that you need to know the Symsoft's AWS account id (Ask someone how nows, if you don't know :-))). 
You login [here](https://aws.amazon.com/)  

2. Select the *CloudFormation* service

3. Click *Create Stack*

4. Choose the option *Specify an Amazon S3 template URL* and enter the following URL:
´´´´
https://s3-eu-west-1.amazonaws.com/codecamp2016/cf_appdemo_template.json
´´´´

5. Enter stack parameters
   * Stack name: Use for example your name
   * ClusterName: Use same as for stack name
   * KeyName: Select *realtime*
   * TeamName: Use same as for stack name

6. Click next and then next again

7. Tick the checkbox *I acknowledge that AWS CloudFormation might create IAM resources* and then click *Create*

8. This will now take a while, so go grab some coffee.  

   

# Running the demo app #
## Building and launching the Customer service ##
1. Go to the customerservice module
2. Build the artifact: 
````
$ mvn clean install
````
3. Build the docker image
````
$ docker build -t <your_dockerhub_name>/customerservice:1 .
````
4. Push the image
````
$ docker push <your_dockerhub_name>/customerservice:1
````
5. aws ecs register-task-definition --cli-input-json file://taskdefinition.json
6. aws ecs create-service --cluster Kalle --service-name CustomerService  --cli-input-json file://servicedefinition.json

