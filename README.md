```
curl --location --request POST 'https://localhost:8443/vault/encrypt' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic dXNlcjpQYXNzd29yZDAx' \
--data-raw '{
	"passphrase": "toto",
	"text": "name:alex"
}'
```

```
curl --location --request POST 'https://localhost:8443/vault/decrypt' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic dXNlcjpQYXNzd29yZDAx' \
--data-raw '{
	"passphrase":"toto",
	"text":"$ANSIBLE_VAULT;1.1;AES256\r\n32336630376230326633366361393365306634326131373230623466396133626563326439653537\r\n3038613433353034643663613730313662303630303465370a326634326563666362366564623064\r\n61663138306632666631646135633064666631303838366333396166663538306366393065356162\r\n6234626230306137330a383063383763613362616237366333343632306135353730303739623963\r\n3262"
}'
```
