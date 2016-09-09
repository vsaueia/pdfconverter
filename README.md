# pdfconverter
Conversor de PDF via API usando Spring Boot

Entradas:
	- txt (itext) - http://stackoverflow.com/questions/4696463/convert-text-into-pdf
	- imagens (jpeg, bitmap, png) - iText (http://www.rgagnon.com/javadetails/java-image-to-pdf-using-itext.html)
	- doc e docx - (POI e iText) - http://stackoverflow.com/questions/6060541/creating-pdf-from-word-doc-using-apache-poi-and-itext-in-java

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

