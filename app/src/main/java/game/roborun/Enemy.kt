package game.roborun

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import kotlin.random.Random

class Enemy(private val screenWidth: Int, private val screenHeight: Int) {

    private val enemies: MutableList<EnemyInstance> = mutableListOf()
    private val speed = 250

    fun update(dt: Float) {
        // Cria um novo inimigo em intervalos aleatórios
        if (Random.nextInt(100) < 2) {
            val newEnemy = EnemyInstance(
                screenWidth.toFloat(),  // Posição inicial à direita da tela
                (screenHeight - 80).toFloat(),  // Ajusta a altura do inimigo para estar no chão
                60f,  // Largura do inimigo
                80f   // Altura do inimigo
            )
            enemies.add(newEnemy)
        }

        // Move todos os inimigos para a esquerda
        val enemiesToRemove = ArrayList<EnemyInstance>()

        for (enemy in enemies) {
            enemy.x -= speed * dt

            // Marca os inimigos que saem completamente da tela para remoção
            if (enemy.x + enemy.width < 0) {
                enemiesToRemove.add(enemy)
            }
        }

        // Remove os inimigos marcados para remoção
        enemies.removeAll(enemiesToRemove)
    }


    fun draw(canvas: Canvas) {
        // Desenha todos os inimigos
        val paint = Paint()
        paint.color = Color.RED  // Cor vermelha (ajuste conforme necessário)
        for (enemy in enemies) {
            canvas.drawRect(enemy.x, enemy.y, enemy.x + enemy.width, enemy.y + enemy.height, paint)
        }
    }

    fun getEnemies(): List<EnemyInstance> {
        return enemies.toList()
    }
    companion object {
        private val instance: Enemy = Enemy(0, 0)  // Você pode ajustar os parâmetros conforme necessário

        fun getInstance(): Enemy {
            return instance
        }
    }
    data class EnemyInstance(
        var x: Float,
        var y: Float,
        var width: Float,
        var height: Float
    )
}