package andre.artus.com.services

import com.madgag.gif.fmsware.AnimatedGifEncoder
import org.bytedeco.javacv.FFmpegFrameGrabber
import org.bytedeco.javacv.Java2DFrameConverter
import org.springframework.stereotype.Service


/**
 * andre.artus.com.services part of justgifit
 * Created by Andre Artus on 2016-10-01.
 */
@Service
open class ConverterService {
  fun toAnimatedGif(frameGrabber: FFmpegFrameGrabber, gifEncoder: AnimatedGifEncoder, start: Int, end: Int?,
                    speed: Int) {
    val startFrame: Long = Math.round(start * frameGrabber.frameRate)

    val endFrame: Long = when (end) {
      null -> frameGrabber.lengthInFrames.toLong()
      else -> Math.round(end * frameGrabber.frameRate)
    }

    val frameConverter = Java2DFrameConverter()
    for (i in startFrame until endFrame) {
      if (i % speed != 0L) continue
      // Bug if frameNumber is set to 0
      if (i > 0) {
        frameGrabber.frameNumber = i.toInt()
      }

      val bufferedImage = frameConverter.convert(frameGrabber.grabImage())
      gifEncoder.addFrame(bufferedImage)

    }

    frameGrabber.stop()
    gifEncoder.finish()
  }
}