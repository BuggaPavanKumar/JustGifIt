package andre.artus.com.services

import org.bytedeco.javacv.FFmpegFrameGrabber
import org.bytedeco.javacv.FrameGrabber
import org.springframework.stereotype.Service
import java.io.File

/**
 * andre.artus.com.services part of justgifit
 * Created by Andre Artus on 2016-10-01.
 */
@Service
class VideoDecoderService {

  @Throws(FrameGrabber.Exception::class)
  fun read(video: File): FFmpegFrameGrabber {
    val frameGrabber = FFmpegFrameGrabber(video)
    frameGrabber.start()
    return frameGrabber
  }
}