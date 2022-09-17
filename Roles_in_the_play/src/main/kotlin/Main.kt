import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException
import java.io.PrintWriter
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption

fun RoleSeparator(filename1: String, outfile1: String)
{
    val file = File(filename1)
    val roles1 = mutableListOf<String>()
    var counter1 = 0;
    var nof1 = 1
    val phrases1 = mutableListOf<String>()
    val amofphr = mutableListOf<Int>()
    val numofhr = mutableListOf<Int>()
    var datawrite: String

    try // Записываем все роли в список
    {
        BufferedReader(FileReader(file)).use { br ->
            var line: String

            while (br.readLine().also { line = it } !=null)
            {
                if (line == "textLines:")
                {
                    break
                }
                else
                {
                    if (line != "roles:")
                    {
                        roles1.add(line)
                    }
                }
                counter1 = counter1 + 1
                amofphr.add(0)
            }
        }
    } catch (e: IOException)
    {
        e.printStackTrace()
    }

    counter1 = counter1 - 2
    try //Распределяем реплики по ролям
    {

        BufferedReader(FileReader(file)).use { br ->
            var line: String?
            var check1 = false


            while (br.readLine().also { line = it } !=null)
            {

                if (line == "textLines:")
                {
                    check1 = true
                    continue
                }

                if (check1)
                {
                    val line1: String = line.toString()
                    for (i in 0..counter1)
                    {
                        if (line1.startsWith(roles1[i]))
                        {
                            phrases1.add("$nof1) ${line1.replace("${roles1[i]}:","")}")
                            amofphr[i] = amofphr[i]+1
                            nof1 = nof1 + 1
                            numofhr.add(i)
                        }
                    }
                }
            }
        }
    } catch (e: IOException)
    {
        e.printStackTrace()
    }
    //Записываем всё в файл
    PrintWriter(outfile1, Charsets.UTF_8).use {it.print("")}
    for (i in 0..counter1)
    {
        println("${roles1[i]}:")
        datawrite = "${roles1[i]}:\n"
        try {
            Files.write(Paths.get(outfile1), datawrite.toByteArray(), StandardOpenOption.APPEND)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        for (j in 0..nof1-2)
        {
            if (numofhr[j] == i)
            {
                println(phrases1[j])
                datawrite = "${phrases1[j]}\n"
                try {
                    Files.write(Paths.get(outfile1), datawrite.toByteArray(), StandardOpenOption.APPEND)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }

    }
}

fun main()
{

    RoleSeparator("roles.txt", "output.txt")

}
