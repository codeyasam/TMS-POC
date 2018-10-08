import FormModal from '../../ui/generic/ModalForm'
import { hideAddApplicationForm } from '../../../actions'
import { connect } from 'react-redux'

const mapStateToProps = (state, props) => ({
    isVisible: state.application.addApplication.addApplicationFormVisibility
})

const mapDispatchToProps = dispatch => ({
    onCloseModal() {
        dispatch(hideAddApplicationForm())
    }
})

export default connect(mapStateToProps, mapDispatchToProps)(FormModal)