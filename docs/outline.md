Video Title            | Description |
---------------------- | -------------
Overview               | Why every BigInsights user should use this repository?
Getting Started        | Initial setup steps you need to perform
WebHDFS Groovy Example | How can I programmatically work with files on the cluster
each example ...       | How can I ...
Testing your Cluster   | How can I test my cluster?
Architecture           | How can I create my own examples?

*********
**Overview**

NOTE: comments in brackets are speaker notes

- Introduction
  - Chris Snow, work for IBM Cloud Data Services
  - 20+ years experience in technology
  - BEng (hons) Mechanical Engineering
  - MBA Technology Management
  - Industry Certifications, Cissp, Linux, Java ...
- What is the BigInsight examples repository?
  - collection of examples for BigInsights
  - code you run against your cluster (with minimal setup)
  - tools you can use on your cluster (again, with minimal setup, tools include zeppelin which is .., squirrel sql which is ..)
- Who should use this repository?
  - Every BigInsights user/developer/admistrator (will probably find at least one example useful)
- Why use the repository?
  - documentation alone doesn't always work (things change and documentation gets broken)
  - see the example running, then adapt (step by step changes to ensure nothing gets broken)
  - sharing something that doesn't work (e.g. you are trying to do something novel and it doesn't work / create an example that can be reproduced by others)
  - How to use the repository
    - Getting Started (one-off setup)
       - go through README on homepage
       - watch the Getting Started video
    - Find an example
    - Run the example
       - go through the example README
       - watch the example video (if available)

**********
**Getting Started**
- Overview
   - Pre-requisites
   - Setup Instructions
   - Running the Examples
   - Getting Help
   - Contributing
- Pre-requisites
- Setup Instructions
   - Clone the repository
   - Change into the repository directory
   - Edit connection.properties
      - what is it?
      - how do we edit it?  any text editor
      - hostname, username, password
      - known_hosts:allowAnyHosts 
         - uncomment for now
         - if you want to know what this does see the extras
   - Download Certificate (why?)
   - Download Libs (why?)
- Running the Examples
   - Click link, show examples
   - Each example has README and Instructions
      - WebHdfs example
- Getting Help - show example post
- Contributing - architecture video planned
- Extras
   - connection.properties
      - known_hosts:allowAnyHosts
         - set this and skip the description if you want
         - many examples use ssh
         - this tells ssh to not verify the connection

**********
