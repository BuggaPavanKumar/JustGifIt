package andre.artus.com.controller

import andre.artus.com.services.ConverterService
import andre.artus.com.services.GifEncoderService
import andre.artus.com.services.VideoDecoderService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Paths
import javax.inject.Inject

/**
 * andre.artus.com.controller part of justgifit
 * Created by Andre Artus on 2016-10-01.
 */

@RestController
class UploadController {

  companion object {
    //val Log = Logger.getLogger(UploadController::class.java.name)
    val log: Logger = LoggerFactory.getLogger(UploadController::class.java)
  }


  @RequestMapping(
      value = "/upload",
      method = arrayOf(RequestMethod.POST),
      produces = arrayOf(MediaType.IMAGE_GIF_VALUE))
  fun upload(@RequestPart("file") file: MultipartFile,
             @RequestParam("start", defaultValue = "0") start: Int = 0,
             @RequestParam("end", defaultValue = "") end: Int? = null,
             @RequestParam("speed", defaultValue = "1") speed: Int = 1,
             @RequestParam("repeat", defaultValue = "no") repeat: Boolean = false): String {
    log.info("Save file to {}", location)
    println("Save file to $location")
    val ext = file.originalFilename.substringAfterLast('/').substringAfterLast('.')
    val currentTimeMillis = System.currentTimeMillis()
    val videoFile = File("$location/$currentTimeMillis.$ext")
    println("Save file to $videoFile")
    file.transferTo(videoFile)
    log.debug("Saved file to absolutePath{}", videoFile.absolutePath)

    val output = Paths.get("$location/gif/$currentTimeMillis.gif")

    val frameGrabber = videoDecoderService.read(videoFile)
    val gifEncoder = gifEncoderService.getGifEncoder(repeat, frameGrabber.frameRate.toFloat(), output)
    converterService.toAnimatedGif(frameGrabber, gifEncoder, start, end, speed)

    log.info("Saved generated gif to {}", output.toString())

    return output.fileName.toString()
  }


  @Value("\${spring.http.multipart.location}")
  lateinit private var location: String


  @GetMapping(value = "/index")
  fun index(): String {
    return location
  }

  @Inject
  private lateinit var converterService: ConverterService

  @Inject
  private lateinit var gifEncoderService: GifEncoderService

  @Inject
  private lateinit var videoDecoderService: VideoDecoderService
}