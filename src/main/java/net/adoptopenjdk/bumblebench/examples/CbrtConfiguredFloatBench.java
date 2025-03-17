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
 * CbrtConfiguredFloatBench score: 90657504.000000 (90.66M 1832.3%)
 *                    uncertainty:   0.2
 * <br>
 * HotSpot Java 17 (Adoptium):
 * <br>
 * CbrtConfiguredFloatBench score: 173138368.000000 (173.1M 1897.0%)
 *                    uncertainty:   0.1%
 * <br>
 * HotSpot Java 21 (BellSoft):
 * <br>
 * CbrtConfiguredFloatBench score: 524950880.000000 (525.0M 2007.9%)
 *                    uncertainty:   0.2%
 * <br>
 * GraalVM Java 22:
 * <br>
 * CbrtConfiguredFloatBench score: 461317952.000000 (461.3M 1995.0%)
 *                    uncertainty:   0.6%
 * <br>
 * HotSpot Java 23 (Adoptium):
 * <br>
 * CbrtConfiguredFloatBench score: 499049824.000000 (499.0M 2002.8%)
 *                    uncertainty:   0.2%
 */
public final class CbrtConfiguredFloatBench extends MicroBench {
	 protected long doBatch (long numIterations) throws InterruptedException {
		 float sum = 0.01f;
		 final float shrink = 16180.339887498949f / numIterations;
		 for (long i = 0; i < numIterations; i++)
			 sum += cbrtConfigured(i + shrink);
		 return numIterations;
	 }

	public static float cbrtDigital(float x) {
		int ix = BitConversion.floatToIntBits(x);
		final int sign = ix & 0x80000000;
		ix &= 0x7FFFFFFF;
		final float x0 = x;
		ix = (ix >>> 2) + (ix >>> 4);
		ix += ix >>> 4;
		ix = ix + (ix >>> 8) + 0x2A5137A0 | sign;
		x = BitConversion.intBitsToFloat(ix);
		x = 0.33333334f * (2f * x + x0 / (x * x));
		x = 0.33333334f * (1.9999999f * x + x0 / (x * x));
		return x;
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
