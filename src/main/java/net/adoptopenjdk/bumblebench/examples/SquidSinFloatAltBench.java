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

import net.adoptopenjdk.bumblebench.core.MicroBench;

/**
 * Windows 10, 10th gen i7 mobile hexacore at 2.6 GHz:
 * <br>
 * HotSpot Java 8 (AdoptOpenJDK):
 * <br>
 * SquidSinFloatAltBench score: 73303128.000000 (73.30M 1811.0%)
 *                   uncertainty:   0.5%
 * <br>
 * OpenJ9 Java 15:
 * <br>
 * SquidSinFloatAltBench score: 66262600.000000 (66.26M 1800.9%)
 *                   uncertainty:   0.6%
 * <br>
 * HotSpot Java 16 (AdoptOpenJDK):
 * <br>
 * SquidSinFloatAltBench score: 71553392.000000 (71.55M 1808.6%)
 *                   uncertainty:   0.5%
 * <br>
 * GraalVM CE Java 16:
 * <br>
 *
 * <br>
 * HotSpot Java 17 (SAP Machine):
 * <br>
 *
 * <br>
 * It just isn't as fast as the regular NumberTools.sin(float).
 */
public final class SquidSinFloatAltBench extends MicroBench {
	public static float sin(float radians)
	{
		radians *= 0.6366197723675814f;
		final int floor = (radians >= 0 ? (int) radians : (int) radians - 1) & -2;
		radians -= floor;
		radians *= 2f - radians;
		return Math.copySign(radians * (-0.775f - 0.225f * radians), ((floor & 2) - 1));
	}

	protected long doBatch(long numIterations) throws InterruptedException {
		float sum = 0.1f;
		for (long i = 0L, bits = 123L; i < numIterations; i++, bits += 0x9E3779B97F4A7C15L) {
			sum -= sin(
					Float.intBitsToFloat(129 - Long.numberOfLeadingZeros(bits) << 23 | ((int) bits & 0x807FFFFF))
			);
		}
		return numIterations;
	}
}
