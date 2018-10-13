import DeleteApplicationForm from '../../ui/applications/DeleteApplicationForm'
import { deleteApplicationRequest, hideDeleteApplicationForm } from '../../../actions'
import { connect } from 'react-redux'

const mapStateToProps = (state, props) => ({
    selectedEntries: state.application.selectedApplicationEntries,
    isDeletingApplication: state.application.deleteApplication.deletingApplication,
    hasError: state.application.deleteApplication.hasErrorOnDeletingApplication,
    searchText: state.application.retrieveApplication.applicationSearchText,
    currentPage: state.application.pagination.paginationPage,
    pageSize: state.application.pagination.paginationSize
})

const mapDispatchToProps = dispatch => ({
    onDeleteApplication(entriesToDelete, searchText, currentPage, pageSize) {
        console.log("on delete")
        dispatch(deleteApplicationRequest(entriesToDelete, searchText, currentPage, pageSize))
    },
    onCancel() {
        dispatch(hideDeleteApplicationForm())
    }
})

export default connect(mapStateToProps, mapDispatchToProps)(DeleteApplicationForm)