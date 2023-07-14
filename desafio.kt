enum class Nivel { BASICO, INTERMEDIARIO, DIFICIL }

class Usuario(val nome: String, val email: String) {
    val formacoesInscritas = mutableListOf<Formacao>()

    fun inscreverEmFormacao(formacao: Formacao) {
        if (!formacoesInscritas.contains(formacao)) {
            formacoesInscritas.add(formacao)
            formacao.matricular(this)
        }
    }

    fun cancelarInscricao(formacao: Formacao) {
        if (formacoesInscritas.contains(formacao)) {
            formacoesInscritas.remove(formacao)
            formacao.cancelarMatricula(this)
        }
    }

    fun visualizarFormacoesInscritas() {
        println("Formações inscritas por $nome:")
        formacoesInscritas.forEach { formacao ->
            println(formacao.nome)
        }
    }
}

data class ConteudoEducacional(val nome: String, val duracao: Int = 60)

data class Formacao(var nome: String, var nivel: Nivel, var vagasDisponiveis: Int, var duracaoTotal: Int, var conteudos: MutableList<ConteudoEducacional>) {
    val inscritos = mutableListOf<Usuario>()

    fun matricular(usuario: Usuario) {
        if (!inscritos.contains(usuario) && vagasDisponiveis > 0) {
            inscritos.add(usuario)
            vagasDisponiveis--
            usuario.formacoesInscritas.add(this)
        }
    }

    fun cancelarMatricula(usuario: Usuario) {
        if (inscritos.contains(usuario)) {
            inscritos.remove(usuario)
            vagasDisponiveis++
            usuario.formacoesInscritas.remove(this)
        }
    }
}

fun main() {
    val conteudo1 = ConteudoEducacional("Introdução ao Kotlin")
    val conteudo2 = ConteudoEducacional("Programação Orientada a Objetos com Kotlin")
    val conteudo3 = ConteudoEducacional("Kotlin Avançado")

    val formacao1 = Formacao("Formação Kotlin Básico", Nivel.BASICO, 10, 180, mutableListOf(conteudo1, conteudo2))
    val formacao2 = Formacao("Formação Kotlin Avançado", Nivel.DIFICIL, 5, 240, mutableListOf(conteudo2, conteudo3))

    val usuario1 = Usuario("João", "joao@example.com")
    val usuario2 = Usuario("Maria", "maria@example.com")

    usuario1.inscreverEmFormacao(formacao1)
    usuario2.inscreverEmFormacao(formacao1)
    usuario2.inscreverEmFormacao(formacao2)

    usuario1.visualizarFormacoesInscritas()
    usuario2.visualizarFormacoesInscritas()
}
