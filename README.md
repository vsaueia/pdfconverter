# pdfconverter
Conversor de PDF via API usando Spring Boot

Entradas:
	- txt (itext) 
	- imagens (jpeg, bitmap, png) 
	- doc e docx - (POI e iText) 
	- 
Configurações:
 	- resolução de saída (dpi)
 	- nome do arquivo de saída
 	- permitir junção ou separação dos arquivos
 	- multiplas entradas	

Saída:
   - PDF  

Desejável:
   - ser pesquisável   
   - receber o arquivo com base64 para diminuição do consumo de banda

Resumo: 
	Fazer uma API capaz de receber arquivos de entrada (podendo ser doc, txt ou imagem) e converter
	em pdf seguindos as configurações escolhidas pelo usuário.
	Dar feedbacks claros sobre sucessos/problemas/status
	Deixar assincrono (controle por token)

