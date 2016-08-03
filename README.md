# Prerequisites  #
## Configure AWS credentials ##
You need to have you AWS access key id and your AWS secret access key.
These keys shall be stored in a filed named *credentials* located in the ~/.aws folder.
The content of the file shall be as follows:

`
  [default]
  aws_access_key_id = <your_access_key_id>
  aws_secret_access_key = <your_secret_access_key>
`

## Install Docker ##
[Docker](https://docs.docker.com)

## Install AWS CLI ##
[AWS Command Line Interface](https://aws.amazon.com/cli/)


# Running the demo app #
## Building and launching the Customer service ##
1. Go to the customerservice module
2. Build the artifact: *mvn clean install*
3. Build the docker image: *docker build -t symsoft/customerservice:1 .*


