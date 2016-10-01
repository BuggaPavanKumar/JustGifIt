package andre.artus.com.services

import com.madgag.gif.fmsware.AnimatedGifEncoder
import org.springframework.stereotype.Service
import java.nio.file.Path

/**
 * andre.artus.com.services part of justgifit
 * Created by Andre Artus on 2016-10-01.
 */

@Service
class GifEncoderService {
  fun getGifEncoder(repeat: Boolean, frameRate: Float, output: Path): AnimatedGifEncoder {
    val gifEncoder = AnimatedGifEncoder()

    if (repeat) {
      gifEncoder.setRepeat(0)
    }

    gifEncoder.setFrameRate(frameRate)
    gifEncoder.start(output.toString())
    return gifEncoder
  }
}

