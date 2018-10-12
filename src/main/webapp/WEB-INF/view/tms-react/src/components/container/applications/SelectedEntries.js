import SelectedEntries from '../../ui/generic/SelectedEntries'
import { clearSelectedApplicationEntries, clearSelectedApplicationEntriesByPage, setPaginationPageRequest } from '../../../actions'
import { connect } from 'react-redux'

const mapStateToProps = (state, props) => ({
    selectedEntries: state.application.selectedApplicationEntries,
    pageSize: state.application.pagination.paginationSize
})

const mapDispatchToProps = dispatch => ({
    onClearSelectedEntries() {
        dispatch(clearSelectedApplicationEntries())
    },
    onClearSelectedEntriesByPage(page) {
        dispatch(clearSelectedApplicationEntriesByPage(page))
    },
    onSetPaginationPage(page, pageSize) {
        dispatch(setPaginationPageRequest(page, pageSize))
    }    
})

export default connect(mapStateToProps, mapDispatchToProps)(SelectedEntries)