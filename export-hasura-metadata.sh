#!/bin/bash

# run this from pom.xml file or commandline to update hasura metadata

# --project:  directory where commands are executed (default: current dir)
hasura metadata export --endpoint http://localhost:7010 --admin-secret test --project hasura