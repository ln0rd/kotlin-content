package OO.Heranca

class Ferrari: Carro(350), Esportivo {
    override var turbo: Boolean = false
        get() = field
        set(value) { field = value }

    override fun acelerar() {
        super.alterarVelocidade( if(turbo) 50 else 125 )
    }

    override fun frear() {
        super.alterarVelocidade( -25 )
    }

}