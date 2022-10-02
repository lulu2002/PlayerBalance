package me.lulu.playerbalance.module

interface RandomModule {
    fun randomValue(from: Int, to: Int): Int
}

class RandomModuleImpl : RandomModule {

    override fun randomValue(from: Int, to: Int): Int {
        return (from..to).random()
    }

}