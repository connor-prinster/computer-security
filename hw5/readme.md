# Homework 5

## To Run
There are two GUIs
* PhishingGUI.java
    * checks those under the Phishing section below
* SQLIGUI.java
    * checks those under the SQLI section below

## Phishing

* royalty or religious figures
* immediacy
* consequences
* sketchy email address
* sketchy urls
* redeem

## SQLI
* char() 
* union 
* -- 
* semicolon 
* tautology 
* initial apostrophe 
* illogical/illegal (convert(), )
* empty space 
* update 
* select 
* from 
* >, < 
* ASCII() 
* substring() 
* piggy-backed (admin' --, '; drop table users --)
* inference (putting 1=0 then 1=1, or ASCII(SUBSTRIN()) > X WAITFOR 5 --) --ish
* alternate encodings (legalUser'; exec(char(0x1098314e)) --, legalUser';exec(SHUTDOWN))