package furhatos.app.templateadvancedskill.utils

import java.io.File
import java.io.FileInputStream
import java.util.*

val prop = Properties()
val file = File("skill.properties")

fun loadProperties(){
    FileInputStream(file).use { prop.load(it) }
}