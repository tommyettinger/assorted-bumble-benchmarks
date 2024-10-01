
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
 * Taxon32RandomLongBench score: 299567296.000000 (299.6M 1951.8%)
 *                    uncertainty:   0.4%
 * <br>
 * HotSpot Java 17 (Adoptium):
 * <br>
 * Taxon32RandomLongBench score: 382746080.000000 (382.7M 1976.3%)
 *                    uncertainty:   1.3%
 * <br>
 * HotSpot Java 21 (BellSoft):
 * <br>
 * Taxon32RandomLongBench score: 389304832.000000 (389.3M 1978.0%)
 *                    uncertainty:   0.4%
 * <br>
 * GraalVM Java 22:
 * <br>
 * Taxon32RandomLongBench score: 360004928.000000 (360.0M 1970.2%)
 *                    uncertainty:   0.7%
 */
public final class Taxon32RandomLongBench extends MicroBench {

	protected long doBatch(long numIterations) throws InterruptedException {
		Taxon32Random rng = new Taxon32Random(0x12345678);
		long sum = 0L;
		for (long i = 0; i < numIterations; i++)
			sum += rng.nextLong();
		return numIterations;
	}
}
