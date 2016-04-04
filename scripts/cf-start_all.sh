#!/bin/bash
cf start messageservice
cf start customer
cf start apigateway
cf start eventanalyzer
