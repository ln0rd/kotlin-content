package br.com.hash.clearing.infrastructure.output.receivables_schedule

import br.com.hash.clearing.domain.mock.FakeProcessableReceivables
import br.com.hash.clearing.domain.output.receivables_schedule.ProcessableReceivables
import br.com.hash.clearing.domain.output.receivables_schedule.ProcessableReceivablesStatus
import br.com.hash.clearing.infrastructure.mock.FakeProcessableReceivablesEntity
import br.com.hash.clearing.infrastructure.transaction.Instance
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import java.time.OffsetDateTime
import kotlin.test.assertEquals

class ProcessableReceivablesRepositoryAdapterTests() : BehaviorSpec({
    Given("Saving a Processing Receivable through Receivables Repository Adapter") {
        val listOfProcessableReceivablesEntity = FakeProcessableReceivablesEntity().createSimpleList()
        val listOfProcessReceivablesDomain = FakeProcessableReceivables().createSimpleList()

        val processableReceivableCrudRepository = mockk<ProcessableReceivablesCrudRepository>()
        val mockedProcessableReceivableAdapter = ProcessableReceivablesRepositoryAdapter(processableReceivableCrudRepository)

        When("we have a list of ProcessableReceivablesEntity to save all") {
            val capturingSlot = slot<List<ProcessableReceivablesEntity>>()
            every { processableReceivableCrudRepository.saveAll(capture(capturingSlot)) } returns listOfProcessableReceivablesEntity

            Then("It need return a ProcessableReceivables list created") {
                val savedProcessableReceivableList: List<ProcessableReceivables> =
                    mockedProcessableReceivableAdapter.save(listOfProcessReceivablesDomain)
                assertEquals(listOfProcessReceivablesDomain, savedProcessableReceivableList)
            }

            Then("It comes at repository with right arguments") {
                val slotCaptured = capturingSlot.captured
                val expectedListOfProcessableReceivablesEntityParameter = FakeProcessableReceivablesEntity().createExpectedInParameterList()
                assertEquals(expectedListOfProcessableReceivablesEntityParameter, slotCaptured)
            }
        }
    }

    Given("Finding a Processing Receivable through Receivables Repository Adapter") {
        val listOfProcessableReceivablesEntity = FakeProcessableReceivablesEntity().createSimpleList()
        val listOfProcessReceivablesDomain = FakeProcessableReceivables().createSimpleList()

        val processableReceivableCrudRepository = mockk<ProcessableReceivablesCrudRepository>()
        val mockedProcessableReceivableAdapter = ProcessableReceivablesRepositoryAdapter(processableReceivableCrudRepository)

        When("Finding by status and owner") {
            val capturingSlotStatus = slot<ProcessableReceivablesStatus>()
            val capturingSlotOwner = slot<String>()
            val capturingSlotDateTime = slot<OffsetDateTime>()

            Then("It need to return a ProcessableReceivable list not sent yet") {
                every {
                    processableReceivableCrudRepository.findByStatusAndOwner(
                        capture(capturingSlotStatus),
                        capture(capturingSlotOwner),
                        capture(capturingSlotDateTime)
                    )
                } returns listOfProcessableReceivablesEntity

                val foundProcessableReceivablesList: List<ProcessableReceivables> =
                    mockedProcessableReceivableAdapter.findNotSent()
                assertEquals(listOfProcessReceivablesDomain, foundProcessableReceivablesList)

                Then("It comes at repository with the right arguments") {
                    assertEquals(true, capturingSlotDateTime.isCaptured)
                    assertEquals(Instance.INSTANCE_ID, capturingSlotOwner.captured)
                    assertEquals(ProcessableReceivablesStatus.WAITING_TO_SEND, capturingSlotStatus.captured)
                }
            }

            When("Anything is found, it would return an empty list") {
                every {
                    processableReceivableCrudRepository.findByStatusAndOwner(
                        capture(capturingSlotStatus),
                        capture(capturingSlotOwner),
                        capture(capturingSlotDateTime)
                    )
                } returns listOf()

                val foundProcessableReceivablesList: List<ProcessableReceivables> =
                    mockedProcessableReceivableAdapter.findNotSent()
                assertEquals(listOf(), foundProcessableReceivablesList)

                Then("It comes at repository with the right arguments") {
                    assertEquals(true, capturingSlotDateTime.isCaptured)
                    assertEquals(Instance.INSTANCE_ID, capturingSlotOwner.captured)
                    assertEquals(ProcessableReceivablesStatus.WAITING_TO_SEND, capturingSlotStatus.captured)
                }
            }
        }
    }
})
