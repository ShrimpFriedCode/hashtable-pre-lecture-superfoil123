GRADING_UNIT_TEST_SUITE_FILE := ts_grading.yaml
GRADING_UNIT_TESTS           := $(strip $(shell sed -E 's/^([A-Za-z0-9]+):.+$$/\1/g' $(GRADING_UNIT_TEST_SUITE_FILE)))
GRADING_UNIT_TEST_FILES      := $(foreach name, $(GRADING_UNIT_TESTS), test/$(name).java)

AUTOGRADING_IMAGE := autograding_java

all: autograde.tar

autograde.tar: autograding.rb ts_handout.yaml ts_grading.yaml test/IUAutogradingRunner.java $(GRADING_UNIT_TEST_FILES)
	tar -chf $@ $^

.PHONY: clean
clean:
	rm -f autograde.tar
