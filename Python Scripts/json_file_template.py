# Source: ChatGPT (readjusted to fit specific needs).
# TODO: not valid for list of lists
def get_key_addresses(d, parent_key=''):
    """
    Recursively get the address of each key in a nested dictionary.

    :param d: A nested dictionary.
    :param parent_key: The parent key used to access the current dictionary.
    :return: A dictionary that maps each key to its address (a list of keys).
    """
    key_addresses = {}
    for key, value in d.items():
        current_key = f"{parent_key}.{key}" if parent_key else key
        key_addresses[current_key] = list(current_key.split("."))
        if isinstance(value, dict):
            sub_key_addresses = get_key_addresses(value, current_key)
            key_addresses.update(sub_key_addresses)
        elif isinstance(value, list):
            for item in value:
                if isinstance(item, dict):
                    sub_key_addresses = get_key_addresses(item, current_key)
                    key_addresses.update(sub_key_addresses)
    return key_addresses


# Source: Notes/json_file_analysis
json_file_blueprint = {
    "all_bugs": {
        "affectedProductsList": [],
        "affectsVersionsList": [],
        "assignee": str,
        "chartStatus": str,
        "componentsList": [],
        "creationDate": str,
        "fieldIssue": str,
        "fixedVersionList": [],
        "jiraID": str,
        "linkedRunIDs": [],
        "linkedTestInstanceIDs": [],
        "numLinkedRunsInGiftedReport": int,
        "numLinkedTestsInGiftedReport": int,
        "priority": str,
        "qcBugID": str,
        "severity": str,
        "status": str,
        "statusChanges": [
            {
                "change_date": str,
                "change_date_obj": {
                    "date": str
                    },
                "new_value": str,
                "old_value": str,
                "qc_bug_ID": str
                }
            ],
        "summary": str,
        "thirdPartyDefect": str
        },

    "applied_filters": {
        "activeBugFilters": {
            "affectedProductsList": str,
            "affectsVersionsList": str,
            "componentsList": str,
            "fixedVersionList": str,
            "jiraID": str,
            "linked": [],
            "priority": [],
            "severity": [],
            "status": [],
            "summary": str
            },

        "activeTCFilters": {
            "currentState": [],
            "currentVersion": str,
            "folderPath": str,
            "instanceID": str,
            "jiraIDList": str,
            "lastState": [],
            "lastVersion": str,
            "name": str,
            "testCaseID": str,
            "testSetName": str,
            "testType": str
            }
        },

    "executive_summary": str,

    "kpi_graphs": [],

    "kpi_summary_groups": [],

    "line_chart_results": {
        "blocked": int,
        "failed": int,
        "goal": int,
        "notCompleted": int,
        "passed": int,
        "snapshotCycle": str,
        "snapshotDate": str,
        "snapshotDateOnly": {
            "date": str
            },
        "total": int
        },

    "module_results": {
        "Blocked": int,
        "Failed": int,
        "No Run": int,
        "Not Completed": int,
        "Passed": int,
        "module": str
        },

    "release_name": str,

    "request_failures": {
        "bugs": [],
        "tc_results": []
        },

    "target_cycle": str,

    "tc_results": {
        "automationCandidate": str,
        "automationExecutionTime": str,
        "creationDate": str,
        "currentState": str,
        "currentVersion": str,
        "estimatedExecutionTime": str,
        "folderPath": str,
        "hasDefectLinkage": str,
        "instanceID": str,
        "jiraIDList": [
            "None"
            ],
        "lastState": str,
        "lastVersion": str,
        "moduleName": str,
        "name": str,
        "releaseExecution": str,
        "releaseExecutionRuns": [
           {
               "executionTime": str,
               "hasDefectLinkage": str,
               "runCycle": str,
               "runCycleID": str,
               "runDate": {
                   "date": str
                   },
               "runID": str,
               "runName": str,
               "runStatus": str
               }
           ],
        "runBugList": [],
        "runList": [
           {
               "executionTime": str,
               "hasDefectLinkage": str,
               "runCycle": str,
               "runCycleID": str,
               "runDate": {
                   "date": str
                   },
               "runID": str,
               "runName": str,
               "runStatus": str
               }
           ],
        "runsWithLinkedDefects": [],
        "testCaseID": str,
        "testSetName": str,
        "testType": str,
        "testVCStatus": str
        },

    "test_execution_data": {
        "automatedExecutionCount": int,
        "blockedCount": int,
        "cycle_name": str,
        "date": str,
        "failedCount": int,
        "manualExecutionCount": int,
        "noRunCount": int,
        "notCompletedCount": int
        }
    }

# Can be computed by calling: get_key_addresses(json_file_blueprint)
key_addresses = {
    'all_bugs': ['all_bugs'],
    'all_bugs.affectedProductsList': ['all_bugs', 'affectedProductsList'],
    'all_bugs.affectsVersionsList': ['all_bugs', 'affectsVersionsList'],
    'all_bugs.assignee': ['all_bugs', 'assignee'],
    'all_bugs.chartStatus': ['all_bugs', 'chartStatus'],
    'all_bugs.componentsList': ['all_bugs', 'componentsList'],
    'all_bugs.creationDate': ['all_bugs', 'creationDate'],
    'all_bugs.fieldIssue': ['all_bugs', 'fieldIssue'],
    'all_bugs.fixedVersionList': ['all_bugs', 'fixedVersionList'],
    'all_bugs.jiraID': ['all_bugs', 'jiraID'],
    'all_bugs.linkedRunIDs': ['all_bugs', 'linkedRunIDs'],
    'all_bugs.linkedTestInstanceIDs': ['all_bugs', 'linkedTestInstanceIDs'],
    'all_bugs.numLinkedRunsInGiftedReport': ['all_bugs', 'numLinkedRunsInGiftedReport'],
    'all_bugs.numLinkedTestsInGiftedReport': ['all_bugs', 'numLinkedTestsInGiftedReport'],
    'all_bugs.priority': ['all_bugs', 'priority'],
    'all_bugs.qcBugID': ['all_bugs', 'qcBugID'],
    'all_bugs.severity': ['all_bugs', 'severity'],
    'all_bugs.status': ['all_bugs', 'status'],
    'all_bugs.statusChanges': ['all_bugs', 'statusChanges'],
    'all_bugs.statusChanges.change_date': ['all_bugs', 'statusChanges','change_date'],
    'all_bugs.statusChanges.change_date_obj': ['all_bugs', 'statusChanges', 'change_date_obj'],
    'all_bugs.statusChanges.change_date_obj.date': ['all_bugs', 'statusChanges', 'change_date_obj', 'date'],
    'all_bugs.statusChanges.new_value': ['all_bugs', 'statusChanges', 'new_value'],
    'all_bugs.statusChanges.old_value': ['all_bugs', 'statusChanges', 'old_value'],
    'all_bugs.statusChanges.qc_bug_ID': ['all_bugs', 'statusChanges', 'qc_bug_ID'],
    'all_bugs.summary': ['all_bugs', 'summary'],
    'all_bugs.thirdPartyDefect': ['all_bugs', 'thirdPartyDefect'],
    
    'applied_filters': ['applied_filters'],
    'applied_filters.activeBugFilters': ['applied_filters', 'activeBugFilters'],
    'applied_filters.activeBugFilters.affectedProductsList': ['applied_filters', 'activeBugFilters', 'affectedProductsList'],
    'applied_filters.activeBugFilters.affectsVersionsList': ['applied_filters', 'activeBugFilters', 'affectsVersionsList'],
    'applied_filters.activeBugFilters.componentsList': ['applied_filters', 'activeBugFilters', 'componentsList'],
    'applied_filters.activeBugFilters.fixedVersionList': ['applied_filters', 'activeBugFilters', 'fixedVersionList'],
    'applied_filters.activeBugFilters.jiraID': ['applied_filters', 'activeBugFilters', 'jiraID'],
    'applied_filters.activeBugFilters.linked': ['applied_filters', 'activeBugFilters', 'linked'],
    'applied_filters.activeBugFilters.priority': ['applied_filters', 'activeBugFilters', 'priority'],
    'applied_filters.activeBugFilters.severity': ['applied_filters', 'activeBugFilters', 'severity'],
    'applied_filters.activeBugFilters.status': ['applied_filters', 'activeBugFilters', 'status'],
    'applied_filters.activeBugFilters.summary': ['applied_filters', 'activeBugFilters', 'summary'],
    'applied_filters.activeTCFilters': ['applied_filters', 'activeTCFilters'],
    'applied_filters.activeTCFilters.currentState': ['applied_filters', 'activeTCFilters', 'currentState'],
    'applied_filters.activeTCFilters.currentVersion': ['applied_filters', 'activeTCFilters', 'currentVersion'],
    'applied_filters.activeTCFilters.folderPath': ['applied_filters', 'activeTCFilters', 'folderPath'],
    'applied_filters.activeTCFilters.instanceID': ['applied_filters', 'activeTCFilters', 'instanceID'],
    'applied_filters.activeTCFilters.jiraIDList': ['applied_filters', 'activeTCFilters', 'jiraIDList'],
    'applied_filters.activeTCFilters.lastState': ['applied_filters', 'activeTCFilters', 'lastState'],
    'applied_filters.activeTCFilters.lastVersion': ['applied_filters', 'activeTCFilters', 'lastVersion'],
    'applied_filters.activeTCFilters.name': ['applied_filters', 'activeTCFilters', 'name'],
    'applied_filters.activeTCFilters.testCaseID': ['applied_filters', 'activeTCFilters', 'testCaseID'],
    'applied_filters.activeTCFilters.testSetName': ['applied_filters', 'activeTCFilters', 'testSetName'],
    'applied_filters.activeTCFilters.testType': ['applied_filters', 'activeTCFilters', 'testType'],
    
    'executive_summary': ['executive_summary'],
    
    'kpi_graphs': ['kpi_graphs'],
    
    'kpi_summary_groups': ['kpi_summary_groups'],
    
    'line_chart_results': ['line_chart_results'],
    'line_chart_results.blocked': ['line_chart_results', 'blocked'],
    'line_chart_results.failed': ['line_chart_results', 'failed'],
    'line_chart_results.goal': ['line_chart_results', 'goal'],
    'line_chart_results.notCompleted': ['line_chart_results', 'notCompleted'],
    'line_chart_results.passed': ['line_chart_results', 'passed'],
    'line_chart_results.snapshotCycle': ['line_chart_results', 'snapshotCycle'],
    'line_chart_results.snapshotDate': ['line_chart_results', 'snapshotDate'],
    'line_chart_results.snapshotDateOnly': ['line_chart_results', 'snapshotDateOnly'],
    'line_chart_results.snapshotDateOnly.date': ['line_chart_results', 'snapshotDateOnly', 'date'],
    'line_chart_results.total': ['line_chart_results', 'total'],
    
    'module_results': ['module_results'],
    'module_results.Blocked': ['module_results', 'Blocked'],
    'module_results.Failed': ['module_results', 'Failed'],
    'module_results.No Run': ['module_results', 'No Run'],
    'module_results.Not Completed': ['module_results', 'Not Completed'],
    'module_results.Passed': ['module_results', 'Passed'],
    'module_results.module': ['module_results', 'module'],
    
    'release_name': ['release_name'],
    
    'request_failures': ['request_failures'],
    'request_failures.bugs': ['request_failures', 'bugs'],
    'request_failures.tc_results': ['request_failures', 'tc_results'],
    
    'target_cycle': ['target_cycle'], 'tc_results': ['tc_results'],
    
    'tc_results.automationCandidate': ['tc_results', 'automationCandidate'],
    'tc_results.automationExecutionTime': ['tc_results', 'automationExecutionTime'],
    'tc_results.creationDate': ['tc_results', 'creationDate'],
    'tc_results.currentState': ['tc_results', 'currentState'],
    'tc_results.currentVersion': ['tc_results', 'currentVersion'],
    'tc_results.estimatedExecutionTime': ['tc_results', 'estimatedExecutionTime'],
    'tc_results.folderPath': ['tc_results', 'folderPath'],
    'tc_results.hasDefectLinkage': ['tc_results', 'hasDefectLinkage'],
    'tc_results.instanceID': ['tc_results', 'instanceID'],
    'tc_results.jiraIDList': ['tc_results', 'jiraIDList'],
    'tc_results.lastState': ['tc_results', 'lastState'],
    'tc_results.lastVersion': ['tc_results', 'lastVersion'],
    'tc_results.moduleName': ['tc_results', 'moduleName'],
    'tc_results.name': ['tc_results', 'name'],
    'tc_results.releaseExecution': ['tc_results', 'releaseExecution'],
    'tc_results.releaseExecutionRuns': ['tc_results', 'releaseExecutionRuns'],
    'tc_results.releaseExecutionRuns.executionTime': ['tc_results', 'releaseExecutionRuns', 'executionTime'],
    'tc_results.releaseExecutionRuns.hasDefectLinkage': ['tc_results', 'releaseExecutionRuns', 'hasDefectLinkage'],
    'tc_results.releaseExecutionRuns.runCycle': ['tc_results', 'releaseExecutionRuns', 'runCycle'],
    'tc_results.releaseExecutionRuns.runCycleID': ['tc_results', 'releaseExecutionRuns', 'runCycleID'],
    'tc_results.releaseExecutionRuns.runDate': ['tc_results', 'releaseExecutionRuns', 'runDate'],
    'tc_results.releaseExecutionRuns.runDate.date': ['tc_results', 'releaseExecutionRuns', 'runDate', 'date'],
    'tc_results.releaseExecutionRuns.runID': ['tc_results', 'releaseExecutionRuns', 'runID'],
    'tc_results.releaseExecutionRuns.runName': ['tc_results', 'releaseExecutionRuns', 'runName'],
    'tc_results.releaseExecutionRuns.runStatus': ['tc_results', 'releaseExecutionRuns', 'runStatus'],
    'tc_results.runBugList': ['tc_results', 'runBugList'], 
    'tc_results.runList': ['tc_results', 'runList'],
    'tc_results.runList.executionTime': ['tc_results', 'runList', 'executionTime'],
    'tc_results.runList.hasDefectLinkage': ['tc_results', 'runList', 'hasDefectLinkage'],
    'tc_results.runList.runCycle': ['tc_results', 'runList', 'runCycle'],
    'tc_results.runList.runCycleID': ['tc_results', 'runList', 'runCycleID'],
    'tc_results.runList.runDate': ['tc_results', 'runList', 'runDate'],
    'tc_results.runList.runDate.date': ['tc_results', 'runList', 'runDate', 'date'],
    'tc_results.runList.runID': ['tc_results', 'runList', 'runID'],
    'tc_results.runList.runName': ['tc_results', 'runList', 'runName'],
    'tc_results.runList.runStatus': ['tc_results', 'runList', 'runStatus'],
    'tc_results.runsWithLinkedDefects': ['tc_results', 'runsWithLinkedDefects'],
    'tc_results.testCaseID': ['tc_results', 'testCaseID'],
    'tc_results.testSetName': ['tc_results', 'testSetName'],
    'tc_results.testType': ['tc_results', 'testType'],
    'tc_results.testVCStatus': ['tc_results', 'testVCStatus'],
    
    'test_execution_data': ['test_execution_data'],
    'test_execution_data.automatedExecutionCount': ['test_execution_data', 'automatedExecutionCount'],
    'test_execution_data.blockedCount': ['test_execution_data', 'blockedCount'],
    'test_execution_data.cycle_name': ['test_execution_data', 'cycle_name'],
    'test_execution_data.date': ['test_execution_data', 'date'],
    'test_execution_data.failedCount': ['test_execution_data', 'failedCount'],
    'test_execution_data.manualExecutionCount': ['test_execution_data', 'manualExecutionCount'],
    'test_execution_data.noRunCount': ['test_execution_data', 'noRunCount'],
    'test_execution_data.notCompletedCount': ['test_execution_data', 'notCompletedCount']
    }
