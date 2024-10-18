
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
 * Taupe32RandomLongBench score: 298191008.000000 (298.2M 1951.3%)
 *                    uncertainty:   1.0%
 * <br>
 * HotSpot Java 17 (Adoptium):
 * <br>
 * Taupe32RandomLongBench score: 302388928.000000 (302.4M 1952.7%)
 *                    uncertainty:   0.8%
 * <br>
 * HotSpot Java 21 (BellSoft):
 * <br>
 * Taupe32RandomLongBench score: 337907040.000000 (337.9M 1963.8%)
 *                    uncertainty:   1.3%
 * <br>
 * GraalVM Java 22:
 * <br>
 * Taupe32RandomLongBench score: 268355168.000000 (268.4M 1940.8%)
 *                    uncertainty:   1.1%
 */
public final class Taupe32RandomLongBench extends MicroBench {

	protected long doBatch(long numIterations) throws InterruptedException {
		Taupe32Random rng = new Taupe32Random(0x12345678);
		long sum = 0L;
		for (long i = 0; i < numIterations; i++)
			sum += rng.nextLong();
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
 * GraalVM Java 22:
 * <br>
 *
 */
