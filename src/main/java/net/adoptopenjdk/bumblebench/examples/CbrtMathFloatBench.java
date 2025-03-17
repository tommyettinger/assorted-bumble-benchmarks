/*******************************************************************************
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*******************************************************************************/

package net.adoptopenjdk.bumblebench.examples;

import com.github.tommyettinger.digital.BitConversion;
import net.adoptopenjdk.bumblebench.core.MicroBench;

/**
 * Windows 11, 12th Gen i7-12800H at 2.40 GHz:
 * <br>
 * HotSpot Java 8 (BellSoft):
 * <br>
 * CbrtMathFloatBench score: 21049864.000000 (21.05M 1686.2%)
 *              uncertainty:   0.2%
 * <br>
 * HotSpot Java 17 (Adoptium):
 * <br>
 * CbrtMathFloatBench score: 33744080.000000 (33.74M 1733.4%)
 *              uncertainty:   0.2%
 * <br>
 * HotSpot Java 21 (BellSoft):
 * <br>
 * CbrtMathFloatBench score: 33476422.000000 (33.48M 1732.6%)
 *              uncertainty:   0.2%
 * <br>
 * GraalVM Java 22:
 * <br>
 * CbrtMathFloatBench score: 153614656.000000 (153.6M 1885.0%)
 *              uncertainty:   0.1%
 * <br>
 * HotSpot Java 23 (Adoptium):
 * <br>
 * CbrtMathFloatBench score: 144037056.000000 (144.0M 1878.6%)
 *              uncertainty:   0.3%
 */
public final class CbrtMathFloatBench extends MicroBench {
	 protected long doBatch (long numIterations) throws InterruptedException {
		 float sum = 0.01f;
		 final float shrink = 16180.339887498949f / numIterations;
		 for (long i = 0; i < numIterations; i++)
			 sum += (float) Math.cbrt(i + shrink);
		 return numIterations;
	 }

	public static float cbrtRetry(float cube) {
		int ix = BitConversion.floatToIntBits(cube);
		float x = BitConversion.intBitsToFloat((ix & 0x7FFFFFFF) / 3 + 0x2A5137A0 | (ix & 0x80000000));
		x = 0.6666667f * x + 0.33333334f * cube / (x * x);
		x = 0.6666665f * x + 0.33333332f * cube / (x * x);
		return x;
	}

	public static float cbrtConfigured(float cube) {
		final int ix = BitConversion.floatToIntBits(cube);
		float x = BitConversion.intBitsToFloat((ix & 0x7FFFFFFF) / 3 + 0x2A513792 | (ix & 0x80000000));
		x = 0.66666615f * x + 0.3333331f * cube / (x * x);
		x = 0.66666660f * x + 0.3333332f * cube / (x * x);
		return x;
	}

}
