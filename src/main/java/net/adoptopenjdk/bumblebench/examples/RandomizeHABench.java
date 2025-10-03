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
 * RandomizeHABench score: 1415530752.000000 (1.416G 2107.1%)
 *              uncertainty:   0.2%
 * <br>
 * HotSpot Java 17 (Adoptium):
 * <br>
 * RandomizeHABench score: 1354302464.000000 (1.354G 2102.7%)
 *              uncertainty:   0.2%
 * <br>
 * HotSpot Java 21 (BellSoft):
 * <br>
 * RandomizeHABench score: 1462460416.000000 (1.462G 2110.3%)
 *              uncertainty:   0.4%
 * <br>
 * GraalVM Java 24:
 * <br>
 * RandomizeHABench score: 1700131840.000000 (1.700G 2125.4%)
 *              uncertainty:   0.2%
 */
public final class RandomizeHABench extends MicroBench {

    public static long randomizeHA(long state) {
        state += state * state | 7L;
        state = (state >>> 27 | state << 37);
        state += state * state | 7L;
        return state ^ state >>> 27;
    }

    protected long doBatch(long numIterations) throws InterruptedException {
		long sum = 0L;
		for (long i = 0; i < numIterations; i++)
			sum += randomizeHA(i);
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
