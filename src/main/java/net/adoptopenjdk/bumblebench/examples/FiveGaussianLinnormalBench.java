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
 * FiveGaussianLinnormalBench score: 88797264.000000 (88.80M 1830.2%)
 *                      uncertainty:   0.4%
 * <br>
 * HotSpot Java 17 (Adoptium):
 * <br>
 * FiveGaussianLinnormalBench score: 87081856.000000 (87.08M 1828.2%)
 *                      uncertainty:   0.4%
 * <br>
 * HotSpot Java 21 (BellSoft):
 * <br>
 * FiveGaussianLinnormalBench score: 103184184.000000 (103.2M 1845.2%)
 *                      uncertainty:   1.0%
 * <br>
 * GraalVM Java 22:
 * <br>
 * FiveGaussianLinnormalBench score: 106505464.000000 (106.5M 1848.4%)
 *                      uncertainty:   0.8
 */
public final class FiveGaussianLinnormalBench extends MicroBench {

	protected long doBatch(long numIterations) throws InterruptedException {
		PouchRandom rng = new PouchRandom(0x12345678);
		double sum = 0.0;
		for (long i = 0; i < numIterations; i++) {
			sum += Linnormal.normal(rng.nextLong());
			sum += Linnormal.normal(rng.nextLong());
			sum += Linnormal.normal(rng.nextLong());
			sum += Linnormal.normal(rng.nextLong());
			sum += Linnormal.normal(rng.nextLong());
        }
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
