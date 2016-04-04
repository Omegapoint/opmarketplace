#!/bin/bash
cf restart messageservice
cf restart customer
cf restart apigateway
cf restart eventanalyzer
