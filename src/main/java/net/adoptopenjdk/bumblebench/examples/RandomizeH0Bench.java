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
 * Windows 11, 12th Gen i7-12800H at 2.40 GHz:
 * <br>
 * HotSpot Java 8 (BellSoft):
 * <br>
 * RandomizeH0Bench score: 1410614656.000000 (1.411G 2106.7%)
 *              uncertainty:   0.9%
 * <br>
 * HotSpot Java 17 (Adoptium):
 * <br>
 * RandomizeH0Bench score: 1450406144.000000 (1.450G 2109.5%)
 *              uncertainty:   0.6%
 * <br>
 * HotSpot Java 21 (BellSoft):
 * <br>
 * RandomizeH0Bench score: 1453166208.000000 (1.453G 2109.7%)
 *              uncertainty:   1.0%
 * <br>
 * GraalVM Java 24:
 * <br>
 * RandomizeH0Bench score: 1707199488.000000 (1.707G 2125.8%)
 *              uncertainty:   0.2%
 */
public final class RandomizeH0Bench extends MicroBench {

    public static long randomizeH0(long state) {
        // Xor-Square-Or with orConstant 7.
        state ^= state * state | 7L;
        // Bitwise right rotation by 27.
        state = (state >>> 27 | state << 37);
        // Xor-Square-Or with orConstant 7.
        state ^= state * state | 7L;
        // Right xor-shift by 27.
        return state ^ state >>> 27;
    }

    protected long doBatch(long numIterations) throws InterruptedException {
		long sum = 0L;
		for (long i = 0; i < numIterations; i++)
			sum += randomizeH0(i);
		return numIterations;
	}
}

// TEMPLATE
/* *
 * Windows 11, 12th Gen i7-12800H at 2.40 GHz:
 * <br>
 * HotSpot Java 8 (BellSoft):
 * <br>
 *
 * <br>
 * HotSpot Java 17 (Adoptium):
 * <br>
 *
 * <br>
 * HotSpot Java 21 (BellSoft):
 * <br>
 *
 * <br>
 * GraalVM Java 24:
 * <br>
 *
 */
