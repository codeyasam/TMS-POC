import SelectedEntries from '../../ui/generic/SelectedEntries'
import { clearSelectedApplicationEntries, clearSelectedApplicationEntriesByPage, setPaginationPageRequest } from '../../../actions/applicationActions'
import { connect } from 'react-redux'

const mapStateToProps = (state, props) => ({
    selectedEntries: state.application.selectedApplicationEntries,
    pageSize: state.application.pagination.paginationSize,
    searchText: state.application.retrieveApplication.applicationSearchText
})

const mapDispatchToProps = dispatch => ({
    onClearSelectedEntries() {
        dispatch(clearSelectedApplicationEntries())
    },
    onClearSelectedEntriesByPage(page) {
        dispatch(clearSelectedApplicationEntriesByPage(page))
    },
    onSetPaginationPage(searchText, page, pageSize) {
        dispatch(setPaginationPageRequest(searchText, page, pageSize))
    }    
})

export default connect(mapStateToProps, mapDispatchToProps)(SelectedEntries)