#!/bin/bash
cf restart messageservice
cf restart customer
cf restart marketplace
cf restart apigateway
cf restart eventanalyzer
