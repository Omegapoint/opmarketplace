#!/bin/bash
heroku ps:restart --app messageservice-opm
heroku ps:restart --app customer-opm
heroku ps:restart --app apigateway-opm
