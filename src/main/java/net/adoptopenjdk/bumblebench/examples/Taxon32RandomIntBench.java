
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
 * Taxon32RandomIntBench score: 661845440.000000 (661.8M 2031.1%)
 *                   uncertainty:   1.4%
 * <br>
 * HotSpot Java 17 (Adoptium):
 * <br>
 * Taxon32RandomIntBench score: 835479744.000000 (835.5M 2054.4%)
 *                   uncertainty:   1.7%
 * <br>
 * HotSpot Java 21 (BellSoft):
 * <br>
 * Taxon32RandomIntBench score: 846859392.000000 (846.9M 2055.7%)
 *                   uncertainty:   1.3%
 * <br>
 * GraalVM Java 22:
 * <br>
 * Taxon32RandomIntBench score: 745694656.000000 (745.7M 2043.0%)
 *                   uncertainty:   0.3%
 */
public final class Taxon32RandomIntBench extends MicroBench {

	protected long doBatch(long numIterations) throws InterruptedException {
		Taxon32Random rng = new Taxon32Random(0x12345678);
		int sum = 0;
		for (long i = 0; i < numIterations; i++)
			sum += rng.nextInt();
		return numIterations;
	}
}
