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
 * RandomizeHSBench score: 1175292416.000000 (1.175G 2088.5%)
 *              uncertainty:   0.5%
 * <br>
 * HotSpot Java 17 (Adoptium):
 * <br>
 * RandomizeHSBench score: 1347453312.000000 (1.347G 2102.1%)
 *              uncertainty:   0.2%
 * <br>
 * HotSpot Java 21 (BellSoft):
 * <br>
 * RandomizeHSBench score: 1338049792.000000 (1.338G 2101.4%)
 *              uncertainty:   0.5%
 * <br>
 * GraalVM Java 24:
 * <br>
 * RandomizeHSBench score: 1555268992.000000 (1.555G 2116.5%)
 *              uncertainty:   0.5%
 */
public final class RandomizeHSBench extends MicroBench {

    public static long randomizeHS(long state) {
        state ^= state * state | 25L;
        state ^= state >>> 26;
        state ^= state * state | 27L;
        return state ^ state >>> 28;
    }

    protected long doBatch(long numIterations) throws InterruptedException {
		long sum = 0L;
		for (long i = 0; i < numIterations; i++)
			sum += randomizeHS(i);
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
