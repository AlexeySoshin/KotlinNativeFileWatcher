import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import platform.darwin.COPYFILE_DATA
import platform.darwin.copyfile
import platform.darwin.copyfile_state_alloc
import platform.darwin.copyfile_state_free
import platform.posix.F_OK
import platform.posix.access
import platform.posix.sleep
import platform.posix.stat


fun main(args: Array<String>) {
    if (args.size != 2) {
        return println("Usage: file_watcher.kexe <path> <interval>")
    }

    val file = File(args[0])
    val interval = args[1].toIntOrNull() ?: 0

    require(file.exists()) {
        "No such file: $file"
    }

    require(interval > 0) {
        "Interval must be positive"
    }

    var modified = file.modified()
    while (true) {
        if (file.modified() > modified) {
            println("\nFile copied: ${file.copyAside()}")

            modified = file.modified()
        }
        print("")
        sleep(interval)
    }
}

data class File(private val filename: String) {
    fun exists(): Boolean {
        return access(filename, F_OK) != -1
    }

    fun modified(): Long = memScoped {
        val result = alloc<stat>()
        stat(filename, result.ptr)

        result.st_mtimespec.tv_sec
    }

    fun copyAside(): String {
        val state = copyfile_state_alloc()
        val copied = generateFilename()

        if (copyfile(filename, copied, state, COPYFILE_DATA) < 0) {
            println("Unable to copy file $filename -> $copied")
        }
        copyfile_state_free(state)
        return copied
    }

    private var count = 0
     private val extension = filename.split("").last()

    private fun generateFilename() = filename.replace(extension, "${++count}.$extension")
}


