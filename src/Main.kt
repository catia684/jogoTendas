import java.io.File

fun criaMenu(): String {
    return "\nBem vindo ao jogo das tendas\n\n1 - Novo jogo\n0 - Sair\n"
}

fun validaTamanhoMapa(numLinhas:Int, numColunas: Int): Boolean {
    var resultado = false
    when (numLinhas) {
        6 -> if (numColunas == 5 || numColunas == 6) {
            resultado= true
        }

        8 -> if (numColunas == 8 || numColunas == 10) {
            resultado= true
        }

        10 -> if (numColunas == 8 || numColunas == 10) {
            resultado= true
        }
    }
    return resultado
}

fun criaLegendaHorizontal(numColunas:Int): String{
    val abecedario="ABCDEFGHIJ"
    var count = 0
    var legendaHorizontal= ""
    while (count<numColunas-1){
        legendaHorizontal=legendaHorizontal+abecedario[count].toString()+" | "
        count++
    }
    legendaHorizontal=legendaHorizontal+abecedario[count].toString()
    return legendaHorizontal
}

fun criaLinha (terreno: Array<Array<String?>>, mostraLegendaVertical: Boolean, numeroLinha:Int):String{
    var count = 0
    var linha = ""
    if (mostraLegendaVertical== true){
        if (numeroLinha<9){
            linha = " "+(numeroLinha+1).toString()+" |"
        }else {
            linha = "10 |"
        }

    }else {
        linha ="   |"
    }
    while (count<terreno[numeroLinha].size-1){
        linha = linha+" "
        when(terreno[numeroLinha][count]){
            "T"-> linha+= "T |"
            "A"-> linha += "\u25B3 |"
            null -> linha +="  |"
        }
        count++
    }
    /*ultima coluna */
    linha = linha+" "
    when(terreno[numeroLinha][count]){
        "T"-> linha+= "T"
        "A"-> linha += "\u25B3"
        null -> linha+= " "

    }
    return linha
}

fun validaDataNascimento(data:String?) : String? {
    var resultado:String? = null
    var dia = 0
    var mes = 0
    var ano = 0
    val dInvalida = "Data invalida"
    var count=0
    if (data == null){
        resultado=dInvalida
    }else if (data.length!=10){
        resultado=dInvalida
    }else {
        while (count<10){
            if (count==2 || count==5){
                if (data[count]!= '-'){
                    resultado=dInvalida
                }
            }else if (!(data[count].isDigit())){
                resultado=dInvalida
            }
            count++
        }
    }
    if (resultado==null && data!=null ){
        dia = data[0].digitToInt()*10 + data[1].digitToInt()
        mes = data[3].digitToInt()*10 + data[4].digitToInt()
        ano = data[6].digitToInt()*1000 + data[7].digitToInt()*100 + data[8].digitToInt()*10 + data[9].digitToInt()
        if (ano>1900 && ano<= 2022 && mes in 1..12){
            if (mes==2) {
                if ((ano % 4 == 0 && ano % 100 != 0) || ano % 400 == 0) {
                    if (dia !in 1..29){
                        resultado= dInvalida
                    }
                } else {
                    if (dia !in 1..28){
                        resultado= dInvalida
                    }
                }
            } else if (mes == 4 || mes == 6 || mes == 9 || mes == 11) {
                if (dia !in 1..30){
                    resultado= dInvalida
                }
            } else {
                if (dia !in 1..31) {
                    resultado = dInvalida
                }
            }
        }else{
            resultado= dInvalida
        }
    }
    if(resultado == null){
        val dataJunta= ano*10000 + mes*100 + dia
        if (dataJunta>= 20041101){
            resultado= "Menor de idade nao pode jogar"
        }
    }
    return resultado
}

fun processaCoordenadas(coordenadasStr: String?, numLines: Int, numColumns: Int): Pair<Int,Int>?{
    var resultado= true
    var linha = 0
    var coluna = 0
    if (coordenadasStr!=null){
        if (coordenadasStr.length!=3){
            resultado= false
        }else if ( coordenadasStr[0].isDigit() && coordenadasStr[1]==','&& coordenadasStr[2].isLetter()){
            linha=coordenadasStr[0].digitToInt()
            coluna=coordenadasStr[2].code - 64
            if (!(linha in 1..numLines)){
                resultado= false
            }
            if (!(coluna in 1..numColumns)){
                resultado= false
            }
        }else {
            resultado=false
        }

    }else {
        resultado= false
    }
    if (resultado==false){
        return null
    }else {
        return Pair(linha-1,coluna-1)
    }
}

fun criaLegendaContadoresHorizontal(contadoresHorizontal: Array<Int?>): String{
    var resultado = ""
    var numeroColunas= contadoresHorizontal.size
    var count = 0
    while (numeroColunas>1){
        if (contadoresHorizontal[count]==null){
            resultado=resultado+"    "
        }else{resultado=resultado+contadoresHorizontal[count].toString()+"   "
        }
        count++
        numeroColunas--

    }
    if (contadoresHorizontal[count]==null){
        resultado=resultado+" "
    }else{
        resultado=resultado+contadoresHorizontal[count].toString()
    }
    return resultado

}

fun leContadoresDoFicheiro(numLines:Int, numColumns: Int, verticais:Boolean): Array<Int?>{
    val nomeDoFicheiro = numLines.toString()+"x"+numColumns.toString()+".txt"
    val linhas  =  File(nomeDoFicheiro).readLines()
    var contadores = ""
    var limite = 0
    var count = 0
    if (verticais==true){
        limite= numColumns
        contadores = linhas[0]
    }else {
        limite= numLines
        contadores= linhas[1]
    }
    val partes = contadores.split(",")
    val resultado: Array<Int?> = arrayOfNulls(limite)
    while (count<limite){
        if (partes[count]!="0"){
            resultado [count]= partes[count].toInt()
        }
        count ++
    }
    return resultado
} //ver como fica quando a coordenada é null//

fun leTerrenoDoFicheiro(numLines: Int, numColumns: Int):Array<Array<String?>>{
    val nomeDoFicheiro = numLines.toString()+"x"+numColumns.toString()+".txt"
    val linhas  =  File(nomeDoFicheiro).readLines()
    var count = 2
    val resultado:Array<Array<String?>> = Array(numLines) { arrayOfNulls(numColumns)}
    while (count<linhas.size){
        val partes= linhas[count].split(",")
        resultado[partes[0].toInt()][partes[1].toInt()]="A"
        count++
    }
    return resultado
}

fun temArvoreAdjacente(terreno:Array<Array<String?>>, coords: Pair<Int, Int>) : Boolean{
    var resultado = false
    if (coords.first>0 ){
        if (terreno[coords.first-1][coords.second]== "A"){
            resultado= true
        }

    }
    if (coords.first < terreno.size -1 ){
        if (terreno[coords.first+1][coords.second]== "A"){
            resultado= true
        }

    }
    if (coords.second>0 ){
        if (terreno[coords.first][coords.second-1]== "A"){
            resultado= true
        }

    }
    if (coords.second< terreno[coords.first].size-1 ){
        if (terreno[coords.first][coords.second+1]== "A"){
            resultado= true
        }

    }
    return resultado
}

fun temTendaAdjacente(terreno:Array<Array<String?>>, coords:Pair<Int, Int>) : Boolean{
    var resultado = false
    if (coords.first>0 ) {
        if (terreno[coords.first - 1][coords.second] == "T") {
            resultado = true
        }
        if (coords.second > 0) {
            if (terreno[coords.first - 1][coords.second - 1] == "T") {
                resultado = true
            }
        }
        if (coords.second < terreno[coords.first].size - 1) {
            if (terreno[coords.first - 1][coords.second + 1] == "T") {
                resultado = true
            }
        }
    }
    if (coords.first< terreno.size -1 ) {
        if (terreno[coords.first + 1][coords.second] == "T") {
            resultado = true
        }
        if (coords.second > 0) {
            if (terreno[coords.first + 1][coords.second - 1] == "T") {
                resultado = true
            }
        }
        if (coords.second < terreno[coords.first].size - 1) {
            if (terreno[coords.first + 1][coords.second + 1] == "T") {
                resultado = true
            }
        }
    }
    if (coords.second > 0) {
        if (terreno[coords.first] [coords.second - 1] == "T") {
            resultado = true
        }
    }
    if (coords.second < terreno[coords.first].size - 1) {
        if (terreno[coords.first] [coords.second + 1] == "T") {
            resultado = true
        }
    }

    return  resultado
}

fun contaTendasColuna(terreno:Array<Array<String?>>, coluna: Int):Int{
    var resultado = 0
    if (coluna < terreno[0].size){
        for (count in 0 until terreno.size){
            if (terreno[count][coluna] == "T"){
                resultado++
            }
        }
    }
    return resultado
}

fun contaTendasLinha(terreno:Array<Array<String?>>, linha: Int):Int{
    var resultado = 0
    if (linha < terreno.size){
        for (count in 0 until terreno[linha].size){
            if (terreno[linha][count] == "T"){
                resultado++
            }
        }
    }
    return resultado
}

fun colocaTenda(terreno:Array<Array<String?>>, coords:Pair<Int, Int>): Boolean{
    var resultado= false
    if(terreno[coords.first][coords.second]=="T"){
        terreno[coords.first][coords.second]= null
        resultado=true
    }else{
        if (terreno[coords.first][coords.second] == null && temArvoreAdjacente(terreno, coords) == true
            && temTendaAdjacente(terreno, coords) != true) {
            resultado = true
        }
        if (resultado==true){
            terreno[coords.first][coords.second]="T"
        }
    }
    return resultado
}

fun terminouJogo(terreno:Array<Array<String?>>,contadoresVerticais: Array<Int?>,contadoresHorizontais: Array<Int?>):Boolean{
    var resultado= true
    var count=0
    var numArvores=0
    var numTendas=0
    while (count<terreno.size) {
        if (contadoresHorizontais[count]!=null){
            numArvores = numArvores + contadoresHorizontais[count]!!
        }
        numTendas += contaTendasLinha(terreno,count)
        count++
    }
    if (numArvores==numTendas){
        count=0
        while (count < terreno.size && resultado == true  ){
            if (contaTendasLinha(terreno,count) != (contadoresHorizontais[count] ?: 0)){
                resultado=false
            }
            count++
        }
        count=0
        while (count < terreno[0].size && resultado == true  ){
            if (contaTendasColuna(terreno,count) != contadoresVerticais[count]?: 0){
                resultado=false
            }
            count++
        }
    }else{
        resultado=false
    }
    return resultado
}

fun criaTerreno(terreno:Array<Array<String?>>,contadoresVerticais: Array<Int?>?,contadoresHorizontais: Array<Int?>?,
                mostraLegendaHorizontal: Boolean = true,mostraLegendaVertical: Boolean = true):String {
    val espacoContadores= "  "
    var resultado = ""
    var count= 0
    if (contadoresVerticais!=null){
        resultado+= espacoContadores+"     "+criaLegendaContadoresHorizontal(contadoresVerticais)+"\n"
    }
    if (mostraLegendaHorizontal== true){
        resultado+="     | "+criaLegendaHorizontal(terreno[0].size)+"\n"
    }
    while (count<terreno.size){
        if (contadoresHorizontais!=null){
            if (contadoresHorizontais[count] == null) {
                resultado += espacoContadores
            }else {
                resultado+= contadoresHorizontais[count].toString()+" "
            }
        }else {
            resultado+=espacoContadores
        }
        resultado+=criaLinha(terreno,mostraLegendaVertical, count)
        if (count!=terreno.size-1){
            resultado+="\n"
        }
        count++
    }
    return resultado
}

fun jogar(terreno: Array<Array<String?>>, numLinhas: Int, numColunas: Int, contadoresHorizontal: Array<Int?>,
          contadoresVerticais: Array<Int?>):Pair<Boolean,Boolean>{
    var resultado= false
    var inseriuSair= false
    println("Coordenadas da tenda? (ex: 1,B)")
    var cordenadaValida: Pair<Int,Int>?= null
    var coordenadas = readln()
    if (coordenadas=="sair"){
        inseriuSair=true
    }
    if (inseriuSair!= true){
        cordenadaValida= processaCoordenadas(coordenadas,numLinhas,numColunas)
        while (cordenadaValida==null && inseriuSair==false){
            println("Coordenadas invalidas")
            println("Coordenadas da tenda? (ex: 1,B)")
            coordenadas= readln()
            if (coordenadas=="sair"){
                inseriuSair=true
            }else{
                cordenadaValida= processaCoordenadas(coordenadas,numLinhas,numColunas)
            }
        }
        if (inseriuSair==false) {
            if (colocaTenda(terreno, cordenadaValida!!)==true  ){
                println (criaTerreno(terreno,contadoresVerticais,contadoresHorizontal ))
                if (terminouJogo(terreno,contadoresVerticais,contadoresHorizontal)){
                    resultado=true
                }

            }else {
                println("Tenda nao pode ser colocada nestas coordenadas")
            }

        }
    }
    val saida:Pair<Boolean,Boolean> = Pair(inseriuSair , resultado)
    return saida
}

fun main(){
    var opcao = ""
    var numLinhas = 0
    var numColunas = 0
    var data= ""
    val inseriuSair= false
    var terreno:Array<Array<String?>> = Array(numLinhas) { arrayOfNulls(numColunas)}
    var contadoresHorizontais: Array<Int?> = arrayOfNulls(numLinhas)
    var contadoresVerticais: Array<Int?> = arrayOfNulls(numColunas)

    println(criaMenu())
    opcao = readln()
    while (opcao != "0" && opcao!="1"){
        println("Opcao invalida")
        println(criaMenu())
        opcao= readln()
    }
    if (opcao=="1"){
        println("Quantas linhas?")
        numLinhas= readln().toIntOrNull()?: 0
        while (numLinhas<=0){
            println("Resposta invalida")
            println("Quantas linhas?")
            numLinhas= readln().toIntOrNull()?: 0
        }
        println("Quantas colunas?")
        numColunas= readln().toIntOrNull()?: 0
        while (numColunas<=0){
            println("Resposta invalida")
            println("Quantas colunas?")
            numColunas= readln().toIntOrNull()?: 0
        }
        if (validaTamanhoMapa(numLinhas,numColunas)==true){
            if (numLinhas==10 && numColunas==10){
                println("Qual a sua data de nascimento? (dd-mm-yyyy)")
                data = readln()
                val dinvalida= "Data invalida"
                while (validaDataNascimento(data) == dinvalida) {
                    println("$dinvalida")
                    println("Qual a sua data de nascimento? (dd-mm-yyyy)")
                    data = readln()
                }
                if (validaDataNascimento(data)!=null) {
                    println ("Menor de idade nao pode jogar")
                    main()
                }
            }
            println("")
            contadoresVerticais= leContadoresDoFicheiro(numLinhas,numColunas, true)
            contadoresHorizontais= leContadoresDoFicheiro(numLinhas,numColunas,false)
            terreno= leTerrenoDoFicheiro(numLinhas,numColunas)
            println(criaTerreno(terreno,contadoresVerticais,contadoresHorizontais))
            var resultadoJogo = jogar(terreno,numLinhas,numColunas, contadoresHorizontais,contadoresVerticais)
            while (resultadoJogo.first==false && resultadoJogo.second== false){
                resultadoJogo = jogar(terreno,numLinhas,numColunas, contadoresHorizontais,contadoresVerticais)

            }
            if (resultadoJogo.first==false){
                if (resultadoJogo.second== true){
                    println("Parabens! Terminou o jogo")
                    main()
                }
            }

        }else{
            println("Terreno invalido")
            main()
        }
    }

}


