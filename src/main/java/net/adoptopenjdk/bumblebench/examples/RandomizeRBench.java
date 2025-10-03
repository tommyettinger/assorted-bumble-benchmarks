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
 * RandomizeRBench score: 1177289216.000000 (1.177G 2088.6%)
 *             uncertainty:   0.5%
 * <br>
 * HotSpot Java 17 (Adoptium):
 * <br>
 * RandomizeRBench score: 1234499968.000000 (1.234G 2093.4%)
 *             uncertainty:   0.8%
 * <br>
 * HotSpot Java 21 (BellSoft):
 * <br>
 * RandomizeRBench score: 1180199936.000000 (1.180G 2088.9%)
 *             uncertainty:   0.4%
 * <br>
 * GraalVM Java 24:
 * <br>
 * RandomizeRBench score: 770471040.000000 (770.5M 2046.3%)
 *             uncertainty:   0.5%
 */
public final class RandomizeRBench extends MicroBench {
    /**
     * A long constant used as a multiplier by the MX3 unary hash and randomizeR().
     */
    public static final long C = 0xBEA225F9EB34556DL;

    /**
     * This is likely not an adequate unary hash on its own; it has not been tested as such.
     * This modifies {@link com.github.tommyettinger.digital.Hasher#mix(long)} to include a XOR and large multiply
     * before running the same code as mix().
     * @param x a typically sequential, adjacent long
     * @return any long
     */
    public static long randomizeR(long x) {
        // Intentionally NOT an XLCG, nor is its inverse, but this is bijective.
        x = (x ^ 7L) * 5555555555555555555L;
        x ^= (x << 23 | x >>> 41) ^ (x << 43 | x >>> 21);
        x *= C;
        return x ^ (x << 11 | x >>> 53) ^ (x << 50 | x >>> 14);
    }

	protected long doBatch(long numIterations) throws InterruptedException {
		long sum = 0L;
		for (long i = 0; i < numIterations; i++)
			sum += randomizeR(i);
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
